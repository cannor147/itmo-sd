package com.github.cannor147.itmo.sd.bank.service

import com.github.cannor147.itmo.sd.bank.converter.UserConverter
import com.github.cannor147.itmo.sd.bank.dto.UserDto
import com.github.cannor147.itmo.sd.bank.model.Account
import com.github.cannor147.itmo.sd.bank.model.User
import com.github.cannor147.itmo.sd.bank.repository.AccountRepository
import com.github.cannor147.itmo.sd.bank.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val userConverter: UserConverter,
) {
    @Transactional
    fun create(login: String): UserDto {
        if (!login.chars().allMatch(Character::isLetterOrDigit)) {
            throw IllegalArgumentException("Login should be alphanumeric")
        } else if (login.length < 4) {
            throw IllegalArgumentException("Login should have at least 4 symbols")
        } else if (login.length > 255) {
            throw IllegalArgumentException("Login should have maximum 255 symbols")
        }

        try {
            val user = userRepository.save(User(login = login))
            accountRepository.save(Account(user = user))
            return userConverter.toUserDto(user)
        } catch (e: Exception) {
            throw IllegalArgumentException("Can't create user with login @$login")
        }
    }

    @Transactional
    fun get(login: String): UserDto = userRepository.findByLogin(login)
        .let { userConverter.require(it, login) }
        .let(userConverter::toUserDto)
}