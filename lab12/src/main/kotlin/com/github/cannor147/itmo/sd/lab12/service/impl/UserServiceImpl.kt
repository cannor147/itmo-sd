package com.github.cannor147.itmo.sd.lab12.service.impl

import com.github.cannor147.itmo.sd.lab12.converter.UserConverter
import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.model.User
import com.github.cannor147.itmo.sd.lab12.repository.UserRepository
import com.github.cannor147.itmo.sd.lab12.service.UserService
import kotlin.jvm.optionals.getOrNull

@ExperimentalStdlibApi
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
) : UserService {
    override fun register(login: String, name: String, currency: Currency) = try {
        userRepository.save(User(login = login, name = name, currency = currency)).let(userConverter::toDto)
    } catch (e: Exception) {
        throw IllegalArgumentException("Can't create user with login '${login}'")
    }

    override fun findById(id: Long) = userRepository.findById(id)
        .getOrNull()
        ?.let(userConverter::toDto)
        ?: throw NoSuchElementException("No such user")

    override fun findByLogin(login: String) = userRepository.findByLogin(login)
        ?.let(userConverter::toDto)
        ?: throw NoSuchElementException("No such user")
}