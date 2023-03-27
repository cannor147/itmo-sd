package com.github.cannor147.itmo.sd.lab11.repository

import com.github.cannor147.itmo.sd.lab11.model.Deal
import com.github.cannor147.itmo.sd.lab11.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DealRepository : JpaRepository<Deal, String> {
    fun findByIdAndDeletedFalse(id: String): Deal?
    fun findByIdAndDeletedFalseAndPaidIsNull(id: String): Deal?
    fun findAllBySellerAndDeletedFalseOrBuyerAndDeletedFalse(seller: String, buyer: String): List<Deal>
    fun findAllByStockInAndDeletedFalseAndPaidIsNull(stocks: List<Stock>): List<Deal>
}

fun DealRepository.findAllOpenAndByStock(stock: Stock) = findAllOpenAndByStocksIn(listOf(stock))

fun DealRepository.findAllOpenAndByStocksIn(stocks: List<Stock>) = findAllByStockInAndDeletedFalseAndPaidIsNull(stocks)

fun DealRepository.findAllMyByLogin(login: String) = findAllBySellerAndDeletedFalseOrBuyerAndDeletedFalse(login, login)

fun DealRepository.findOpenById(id: String) = findByIdAndDeletedFalseAndPaidIsNull(id)
