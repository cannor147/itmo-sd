package com.github.cannor147.itmo.sd.lab12.controller

import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/user"])
class UserController(
    private val userService: UserService
) {
    @PostMapping(value = ["/register"])
    suspend fun register(
        @RequestParam(name = "login") login: String,
        @RequestParam(name = "name") name: String,
        @RequestParam(name = "currency", required = false) currency: Currency = Currency.USD,
    ) = userService.register(login, name, currency).let { ResponseEntity.ok(it) }

    @PostMapping(value = ["/{id}"])
    suspend fun search(
        @PathVariable(name = "id") id: Long,
    ) = userService.findById(id).let { ResponseEntity.ok(it) }
}