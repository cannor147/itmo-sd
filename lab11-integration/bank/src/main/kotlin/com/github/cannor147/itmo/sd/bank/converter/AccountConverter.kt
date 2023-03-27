package com.github.cannor147.itmo.sd.bank.converter

import com.github.cannor147.itmo.sd.bank.dto.AccountDto
import com.github.cannor147.itmo.sd.bank.model.Account
import com.github.cannor147.itmo.sd.bank.model.User
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