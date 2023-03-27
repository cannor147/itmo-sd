package com.github.cannor147.itmo.sd.lab11.service

import com.github.cannor147.itmo.sd.lab11.api.exchange.ExchangeCompanyApi
import com.github.cannor147.itmo.sd.lab11.api.exchange.ExchangeDealsApi
import com.github.cannor147.itmo.sd.lab11.converter.AccountConverter
import com.github.cannor147.itmo.sd.lab11.converter.UserConverter
import com.github.cannor147.itmo.sd.lab11.dto.CompanyDto
import com.github.cannor147.itmo.sd.lab11.dto.DealDto
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import com.github.cannor147.itmo.sd.lab11.model.Deal
import com.github.cannor147.itmo.sd.lab11.model.Stock
import com.github.cannor147.itmo.sd.lab11.repository.AccountRepository
import com.github.cannor147.itmo.sd.lab11.repository.DealRepository
import com.github.cannor147.itmo.sd.lab11.repository.StockRepository
import com.github.cannor147.itmo.sd.lab11.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class ExchangeService(
    private val exchangeCompanyApi: ExchangeCompanyApi,
    private val exchangeDealsApi: ExchangeDealsApi,
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val userConverter: UserConverter,
    private val accountConverter: AccountConverter,
    private val stockRepository: StockRepository,
    private val dealRepository: DealRepository,
) {
    fun getCompanies(login: String): List<CompanyDto> {
        val user = userRepository.findByLogin(login).let { userConverter.require(it, login) }
        val companies = exchangeCompanyApi.getAll().body.orEmpty().associateBy(CompanyDto::code).toMutableMap()
        val deals = exchangeDealsApi.getMy(user.login!!, true)
        deals.body.orEmpty().filter { !companies.containsKey(it.companyCode) }.forEach {
            companies[it.companyCode] = CompanyDto(it.companyCode, stockPrice = 0.0, stockAmount = 0)
        }
        deals.body.orEmpty().forEach {
            when {
                it.seller == login && it.buyer == null -> companies[it.companyCode]!!.openSellingDeals += it.amount
                it.buyer == login && it.seller == null -> companies[it.companyCode]!!.openBuyingDeals += it.amount
            }
        }
        companies
            .mapKeys { exchangeCompanyApi.getDeals(it.key).body!!.filter { it.seller != null && it.seller != login } }
            .forEach { (deals, company) -> company.deals.addAll(deals) }
        stockRepository.findByUser(user).filter { companies.containsKey(it.code) }.forEach {
            companies[it.code]!!.myAmount = it.amount
            companies[it.code]!!.myHold = it.hold
        }
        return companies.values.toList()
    }

    @Transactional
    fun buy(login: String, dealId: String, accountName: String) {
        val user = userRepository.findByLogin(login).let { userConverter.require(it, login) }
        val account = accountConverter.require(
            accountRepository.findByUserAndName(user, accountName),
            user,
            accountName
        )

        lateinit var prerequestDeal: DealDto
        try {
            prerequestDeal = exchangeDealsApi.get(id = dealId, login = login).body!!
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message, e)
        }
        val code = prerequestDeal.companyCode
        val price = prerequestDeal.price ?: 0.0
        if (account.amount < prerequestDeal.amount * price * 1.05) {
            throw IllegalArgumentException("Insufficient funds for buying stocks of '$code")
        }

        lateinit var deal: MyDealDto
        try {
            deal = exchangeDealsApi.buy(dealId, login).body!!
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message, e)
        }

        account.amount -= deal.amount * deal.price
        accountRepository.save(account)

        val stock = stockRepository.findByUserAndCode(user, code) ?: Stock(user = user, code = code)
        stock.amount += deal.amount
        stockRepository.save(stock)
    }

    @Transactional
    fun sell(login: String, code: String, amount: Long, accountName: String) {
        val user = userRepository.findByLogin(login).let { userConverter.require(it, login) }
        val account = accountConverter.require(
            accountRepository.findByUserAndName(user, accountName),
            user,
            accountName
        )
        val stock = stockRepository.findByUserAndCode(user, code) ?: Stock(user = user, code = code)
        if (stock.amount < amount) {
            throw IllegalArgumentException("Insufficient funds for selling stocks of '$code")
        }

        stock.amount -= amount
        stock.hold += amount
        stockRepository.save(stock)
        lateinit var deal: MyDealDto
        try {
            deal = exchangeCompanyApi.sell(code, login, amount, false).body!!
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message, e)
        }
        dealRepository.save(Deal(id = deal.id, account = account.name, user = user))
    }
}