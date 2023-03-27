package com.github.cannor147.itmo.sd.lab11.api.exchange

import com.github.cannor147.itmo.sd.lab11.dto.CompanyDto
import com.github.cannor147.itmo.sd.lab11.dto.DealDto
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("exchangeCompanyApi", url = "http://localhost:8080/api/v1/company")
interface ExchangeCompanyApi {
    @PostMapping("/list")
    fun getAll() : ResponseEntity<List<CompanyDto>>

    @GetMapping("/{code}/sell")
    fun sell(
        @PathVariable("code") code: String,
        @RequestParam("login") login: String,
        @RequestParam("amount") amount: Long,
        @RequestParam("auto-renewable", required = false) autoRenewable: Boolean = false,
    ) : ResponseEntity<MyDealDto>


    @GetMapping("/{code}/deals")
    fun getDeals(
        @PathVariable("code") code: String,
    ) : ResponseEntity<List<DealDto>>
}