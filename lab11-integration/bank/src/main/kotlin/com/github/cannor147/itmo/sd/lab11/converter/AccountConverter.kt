package com.github.cannor147.itmo.sd.lab11.converter

import com.github.cannor147.itmo.sd.lab11.dto.AccountDto
import com.github.cannor147.itmo.sd.lab11.model.Account
import com.github.cannor147.itmo.sd.lab11.model.User
import org.springframework.stereotype.Component

@Component
class AccountConverter {
    fun toAccountDto(account: Account) = AccountDto(
        name = account.name,
        amount = account.amount,
        hold = account.hold,
    )

    fun require(account: Account?, user: User, accountName: String) = account
        ?: throw IllegalArgumentException("User @${user.login} has no account with name '$accountName'")
}