package com.github.cannor147.itmo.sd.lab11.api.exchange

import com.github.cannor147.itmo.sd.lab11.dto.DealDto
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("exchangeDealsApi", url = "http://localhost:8080/api/v1/deals")
interface ExchangeDealsApi {
    @GetMapping("/{id}")
    fun get(
        @PathVariable("id") id: String,
        @RequestParam("login") login: String,
    ) : ResponseEntity<DealDto>

    @GetMapping("/my")
    fun getMy(
        @RequestParam("login") login: String,
        @RequestParam("deleted", required = false) includeDeleted: Boolean = false,
    ) : ResponseEntity<List<MyDealDto>>

    @PostMapping("/{id}/buy")
    fun buy(
        @PathVariable("id") id: String,
        @RequestParam("login") login: String,
        @RequestParam("amount", required = false) amount: Long? = null,
    ) : ResponseEntity<MyDealDto>
}