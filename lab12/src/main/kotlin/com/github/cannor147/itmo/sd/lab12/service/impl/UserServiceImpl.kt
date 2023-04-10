package com.github.cannor147.itmo.sd.lab12.service.impl

import com.github.cannor147.itmo.sd.lab12.converter.UserConverter
import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.model.User
import com.github.cannor147.itmo.sd.lab12.repository.UserRepository
import com.github.cannor147.itmo.sd.lab12.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ExperimentalStdlibApi
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userConverter: UserConverter,
) : UserService {
    override suspend fun register(login: String, name: String, currency: Currency) = try {
        userRepository.save(User(login = login, name = name, currency = currency)).let(userConverter::toDto)
    } catch (e: Exception) {
        throw IllegalArgumentException("Can't create user with login '${login}'")
    }

    override suspend fun findById(id: Long) = userRepository.findById(id)
        ?.let(userConverter::toDto)
        ?: throw NoSuchElementException("No such user")

    override suspend fun findByLogin(login: String) = withContext(Dispatchers.IO) {
        userRepository.findByLogin(login)
            .map(userConverter::toDto)
            .block() ?: throw NoSuchElementException("No such user")
    }
}