package com.github.cannor147.itmo.sd.bank.repository

import com.github.cannor147.itmo.sd.bank.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String): User?
}
