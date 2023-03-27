package com.github.cannor147.itmo.sd.lab11.converter

import com.github.cannor147.itmo.sd.lab11.dto.CompanyDto
import com.github.cannor147.itmo.sd.lab11.model.Deal
import com.github.cannor147.itmo.sd.lab11.model.Stock
import com.github.cannor147.itmo.sd.lab11.repository.DealRepository
import com.github.cannor147.itmo.sd.lab11.repository.findAllOpenAndByStock
import com.github.cannor147.itmo.sd.lab11.repository.findAllOpenAndByStocksIn
import org.springframework.stereotype.Component

@Component
class CompanyConverter(
    private val dealRepository: DealRepository,
) {
    fun toCompanyDto(
        stock: Stock,
        deals: List<Deal> = dealRepository.findAllOpenAndByStock(stock),
    ) = CompanyDto(
        code = stock.code!!,
        name = stock.company!!,
        stockPrice = stock.price!!,
        stockAmount = deals.filter { it.buyer == null && it.seller != null }.sumOf { it.amount!! }
    )

    fun toCompanyDto(
        stocks: List<Stock>,
        deals: List<Deal> = dealRepository.findAllOpenAndByStocksIn(stocks),
    ) = stocks.map { stock -> toCompanyDto(stock, deals.filter { deal -> deal.stock?.id == stock.id }) }

    fun require(stock: Stock?): Stock = stock ?: throw NoSuchElementException(NO_SUCH_COMPANY_MESSAGE)

    companion object {
        private const val NO_SUCH_COMPANY_MESSAGE = "No such company"
    }
}