package com.github.cannor147.itmo.sd.bank.dto

data class UserDto(
    val login: String,
    val accounts: List<AccountDto>,
)