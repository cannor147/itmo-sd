package com.github.cannor147.itmo.sd.lab12.dto

import com.github.cannor147.itmo.sd.lab12.model.Currency

data class UserDto(
    val id: Long,
    var login: String,
    var name: String,
    var currerncy: Currency,
)
