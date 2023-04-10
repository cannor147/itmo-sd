package com.github.cannor147.itmo.sd.lab12.service

import com.github.cannor147.itmo.sd.lab12.dto.UserDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.stereotype.Service

@Service
interface UserService {
    suspend fun register(login: String, name: String, currency: Currency = Currency.USD): UserDto
    suspend fun findById(id: Long): UserDto
    suspend fun findByLogin(login: String): UserDto
}