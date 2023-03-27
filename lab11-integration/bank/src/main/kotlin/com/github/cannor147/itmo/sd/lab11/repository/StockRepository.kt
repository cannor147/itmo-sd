package com.github.cannor147.itmo.sd.lab11.repository

import com.github.cannor147.itmo.sd.lab11.model.Stock
import com.github.cannor147.itmo.sd.lab11.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository : JpaRepository<Stock, Long> {
    fun findByUserAndCode(user: User, code: String): Stock?
    fun findByUser(user: User): List<Stock>
}
