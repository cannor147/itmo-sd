package com.github.cannor147.itmo.sd.lab12.service

import com.github.cannor147.itmo.sd.lab12.dto.UserDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun register(login: String, name: String, currency: Currency = Currency.USD): UserDto
    fun findById(id: Long): UserDto
    fun findByLogin(login: String): UserDto
}