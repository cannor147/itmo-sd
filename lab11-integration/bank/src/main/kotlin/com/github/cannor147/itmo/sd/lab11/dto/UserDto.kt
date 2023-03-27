package com.github.cannor147.itmo.sd.lab11.dto

data class UserDto(
    val login: String,
    val accounts: List<AccountDto>,
)