package com.github.cannor147.itmo.sd.lab12.converter

import com.github.cannor147.itmo.sd.lab12.dto.UserDto
import com.github.cannor147.itmo.sd.lab12.model.User
import org.springframework.stereotype.Component

@Component
class UserConverter {
    fun toDto(user: User) = UserDto(
        id = user.id,
        login = user.login!!,
        name = user.name!!,
        currerncy = user.currency!!,
    )
}