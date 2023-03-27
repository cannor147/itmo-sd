package com.github.cannor147.itmo.sd.lab11.repository

import com.github.cannor147.itmo.sd.lab11.model.Deal
import com.github.cannor147.itmo.sd.lab11.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DealRepository : JpaRepository<Deal, Long> {
    fun findAllByUserAndHandledFalse(user: User)
}
