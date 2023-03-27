package com.github.cannor147.itmo.sd.bank.repository

import com.github.cannor147.itmo.sd.bank.model.Account
import com.github.cannor147.itmo.sd.bank.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUserAndName(user: User, name: String): Account?
    fun findByUser(user: User): List<Account>
}
