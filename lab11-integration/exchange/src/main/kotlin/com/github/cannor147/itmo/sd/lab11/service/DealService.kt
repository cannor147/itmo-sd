package com.github.cannor147.itmo.sd.lab11.service

import com.github.cannor147.itmo.sd.lab11.converter.CompanyConverter
import com.github.cannor147.itmo.sd.lab11.converter.DealConverter
import com.github.cannor147.itmo.sd.lab11.dto.DealDto
import com.github.cannor147.itmo.sd.lab11.dto.DealMode
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import com.github.cannor147.itmo.sd.lab11.dto.OpenDealDto
import com.github.cannor147.itmo.sd.lab11.model.Deal
import com.github.cannor147.itmo.sd.lab11.repository.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class DealService(
    private val stockRepository: StockRepository,
    private val companyConverter: CompanyConverter,
    private val dealRepository: DealRepository,
    private val dealConverter: DealConverter,
) {
    @Transactional
    fun get(id: String, login: String): DealDto = dealRepository.findAvailableByIdAndLogin(id, login)
        .let(dealConverter::require)
        .let { dealConverter.toDealDto(it, login) }

    @Transactional
    fun getByLogin(login: String, includeDeleted: Boolean) = dealRepository.findAllMyByLogin(login, includeDeleted)
        .map(dealConverter::toMyDealDto)

    @Transactional
    fun getByCompany(code: String): List<OpenDealDto> = stockRepository.findByCodeAndDeletedFalse(code)
        .let(companyConverter::require)
        .let(dealRepository::findAllOpenAndByStock)
        .map(dealConverter::toOpenDealDto)

    @Transactional
    fun createToSell(login: String, amount: Long, code: String, autoRenewable: Boolean) =
        stockRepository.findByCodeAndDeletedFalse(code)
            .let(companyConverter::require)
            .let { Deal(stock = it, seller = login, amount = validateAmount(amount), autoRenewable = autoRenewable) }
            .let(dealRepository::save)
            .let(dealConverter::toMyDealDto)

    @Transactional
    fun createToBuy(login: String, amount: Long, code: String, autoRenewable: Boolean) =
        stockRepository.findByCodeAndDeletedFalse(code)
            .let(companyConverter::require)
            .let { Deal(stock = it, buyer = login, amount = validateAmount(amount), autoRenewable = autoRenewable) }
            .let(dealRepository::save)
            .let(dealConverter::toMyDealDto)

    @Transactional
    fun buyOrSell(id: String, login: String, amount: Long?, mode: DealMode): MyDealDto {
        val deal = dealConverter.requireOpen(dealRepository.findOpenById(id))
        val stock = deal.stock
        val buyer = deal.buyer
        val seller = deal.seller
        val finalAmount = amount ?: deal.amount!!
        when {
            stock == null || stock.deleted -> throw IllegalAccessException("Stocks are unavailable for this company")
            buyer == null && seller == null -> throw IllegalStateException("Deal is not available to buy or sell")
            buyer == login || seller == login -> throw IllegalArgumentException("Buyer and seller must be different")
            mode == DealMode.BUY && buyer != null -> throw IllegalArgumentException("Deal is not available to buy")
            mode == DealMode.SELL && seller != null -> throw IllegalArgumentException("Deal is not available to sell")
            deal.amount!! < validateAmount(finalAmount) -> throw IllegalArgumentException("Deal requires less amount")
        }

        when (mode) {
            DealMode.BUY -> deal.buyer = login
            DealMode.SELL -> deal.seller = login
        }
        deal.finalPrice = stock!!.price
        deal.paid = java.time.LocalDateTime.now()
        val closedDeal = dealConverter.toMyDealDto(dealRepository.save(deal))
        if (deal.autoRenewable && deal.amount!! > finalAmount) {
            dealRepository.save(
                Deal(
                    stock = stock,
                    buyer = buyer,
                    seller = seller,
                    amount = deal.amount!! - finalAmount,
                    autoRenewable = true,
                )
            )
        }
        return closedDeal
    }

    @Transactional
    fun delete(id: String, login: String) {
        val deal = dealConverter.requireOpen(dealRepository.findOpenById(id))
        if (deal.buyer != login && deal.seller != login) {
            throw IllegalCallerException("Deal is not available to you")
        }
        deal.deleted = true
        dealRepository.save(deal)
    }

    private fun validateAmount(amount: Long) = amount.takeUnless { it <= 0 }
        ?: throw IllegalArgumentException("Amount can't be less than zero")

}