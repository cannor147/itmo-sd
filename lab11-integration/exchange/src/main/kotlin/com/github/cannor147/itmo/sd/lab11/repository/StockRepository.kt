package com.github.cannor147.itmo.sd.lab11.repository

import com.github.cannor147.itmo.sd.lab11.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository : JpaRepository<Stock, Long> {
    fun findByCodeAndDeletedFalse(code: String): Stock?
    fun findAllByDeletedFalse(): List<Stock>
    fun searchAllByCompanyLikeIgnoreCaseAndDeletedFalse(company: String): List<Stock>
}
