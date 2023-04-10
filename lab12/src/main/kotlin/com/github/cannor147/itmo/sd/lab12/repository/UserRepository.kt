package com.github.cannor147.itmo.sd.lab12.repository

import com.github.cannor147.itmo.sd.lab12.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String): User?
}