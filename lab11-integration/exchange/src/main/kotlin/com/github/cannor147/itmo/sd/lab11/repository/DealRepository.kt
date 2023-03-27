package com.github.cannor147.itmo.sd.lab11.repository

import com.github.cannor147.itmo.sd.lab11.model.Deal
import com.github.cannor147.itmo.sd.lab11.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DealRepository : JpaRepository<Deal, String> {
    fun findByIdAndSellerOrIdAndBuyerOrIdAndDeletedFalse(
        id: String,
        login: String,
        id2: String = id,
        login2: String = login,
        id3: String = id
    ): Deal?

    fun findByIdAndDeletedFalseAndPaidIsNull(id: String): Deal?
    fun findAllBySellerOrBuyer(seller: String, buyer: String): List<Deal>
    fun findAllBySellerAndDeletedFalseOrBuyerAndDeletedFalse(seller: String, buyer: String): List<Deal>
    fun findAllByStockInAndDeletedFalseAndPaidIsNull(stocks: List<Stock>): List<Deal>
}

fun DealRepository.findAllOpenAndByStock(stock: Stock) = findAllOpenAndByStocksIn(listOf(stock))

fun DealRepository.findAllOpenAndByStocksIn(stocks: List<Stock>) = findAllByStockInAndDeletedFalseAndPaidIsNull(stocks)

fun DealRepository.findAllMyByLogin(login: String, includeDeleted: Boolean) = if (includeDeleted) {
    findAllBySellerOrBuyer(login, login)
} else {
    findAllBySellerAndDeletedFalseOrBuyerAndDeletedFalse(login, login)
}

fun DealRepository.findAvailableByIdAndLogin(id: String, login: String) =
    findByIdAndSellerOrIdAndBuyerOrIdAndDeletedFalse(id, login)

fun DealRepository.findOpenById(id: String) = findByIdAndDeletedFalseAndPaidIsNull(id)
