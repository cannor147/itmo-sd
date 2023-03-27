package com.github.cannor147.itmo.sd.lab11.service

import com.github.cannor147.itmo.sd.lab11.converter.AccountConverter
import com.github.cannor147.itmo.sd.lab11.converter.UserConverter
import com.github.cannor147.itmo.sd.lab11.dto.UserDto
import com.github.cannor147.itmo.sd.lab11.model.Account
import com.github.cannor147.itmo.sd.lab11.repository.AccountRepository
import com.github.cannor147.itmo.sd.lab11.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val userConverter: UserConverter,
    private val accountConverter: AccountConverter,
) {
    @Transactional
    fun create(login: String, accountName: String): UserDto {
        val user = userRepository.findByLogin(login) ?: throw NoSuchElementException("No such user")
        try {
            accountRepository.save(Account(user = user, name = accountName))
        } catch (e: Exception) {
            throw IllegalArgumentException("Can't create account with name '$accountName' for user @$login")
        }
        return userConverter.toUserDto(user)
    }

    @Transactional
    fun deposit(amount: Double, login: String, accountName: String = "default"): UserDto {
        val user = userConverter.require(userRepository.findByLogin(login), login)
        val account = accountConverter.require(
            accountRepository.findByUserAndName(user, accountName),
            user,
            accountName
        )

        account.amount += amount
        accountRepository.save(account)
        return userConverter.toUserDto(user)
    }

    @Transactional
    fun withdraw(amount: Double, login: String, accountName: String = "default"): UserDto {
        val user = userConverter.require(userRepository.findByLogin(login), login)
        val account = accountConverter.require(
            accountRepository.findByUserAndName(user, accountName),
            user,
            accountName
        )

        if (account.amount < amount) {
            throw IllegalArgumentException("Insufficient funds in tha account '$accountName")
        }
        account.amount -= amount
        accountRepository.save(account)
        return userConverter.toUserDto(user)
    }

    @Transactional
    fun transfer(
        amount: Double,
        fromLogin: String,
        toLogin: String,
        fromAccountName: String = "default",
        toAccountName: String = "default"
    ): UserDto {
        val fromUser = userConverter.require(userRepository.findByLogin(fromLogin), fromLogin)
        val fromAccount = accountConverter.require(
            accountRepository.findByUserAndName(fromUser, fromAccountName),
            fromUser,
            fromAccountName
        )
        val toUser = userConverter.require(userRepository.findByLogin(toLogin), toLogin, skip = true)
        val toAccount = accountConverter.require(
            accountRepository.findByUserAndName(toUser, toAccountName),
            toUser,
            toAccountName
        )

        if (fromAccount.amount < amount) {
            throw IllegalArgumentException("Insufficient funds in tha account '$fromAccountName")
        }
        fromAccount.amount -= amount
        toAccount.amount += amount
        accountRepository.saveAll(listOf(fromAccount, toAccount))
        return userConverter.toUserDto(fromUser)
    }
}