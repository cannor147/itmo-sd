package com.github.cannor147.itmo.sd.lab11.converter

import com.github.cannor147.itmo.sd.lab11.dto.UserDto
import com.github.cannor147.itmo.sd.lab11.model.User
import com.github.cannor147.itmo.sd.lab11.repository.AccountRepository
import org.springframework.stereotype.Component

@Component
class UserConverter(
    private val accountRepository: AccountRepository,
    private val accountConverter: AccountConverter,
) {
    fun toUserDto(user: User) = UserDto(
        login = user.login!!,
        accounts = accountRepository.findByUser(user).map(accountConverter::toAccountDto)
    )

    fun require(user: User?, login: String, skip: Boolean = false) = user ?: if (skip) {
        throw IllegalArgumentException("No user with login @$login")
    } else {
        throw NoSuchElementException("No user with login @$login")
    }
}