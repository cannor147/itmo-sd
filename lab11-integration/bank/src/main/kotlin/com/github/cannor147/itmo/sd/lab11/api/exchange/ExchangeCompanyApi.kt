package com.github.cannor147.itmo.sd.lab11.api.exchange

import com.github.cannor147.itmo.sd.lab11.dto.CompanyDto
import com.github.cannor147.itmo.sd.lab11.dto.DealDto
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@FeignClient("exchangeCompanyApi", url = "http://localhost:8080/api/v1/company")
interface ExchangeCompanyApi {
    @GetMapping("/{code}")
    fun get(
        @PathVariable("code") code: String,
    ) : ResponseEntity<CompanyDto>

    @PostMapping("/search")
    fun search(
        @RequestParam("name") name: String,
    ) : ResponseEntity<List<CompanyDto>>

    @PostMapping("/list")
    fun getAll() : ResponseEntity<List<CompanyDto>>

    @GetMapping("/{code}/deals")
    fun getDeals(
        @PathVariable("code") code: String,
    ) : ResponseEntity<List<DealDto>>

    @GetMapping("/{code}/buy")
    fun buy(
        @PathVariable("code") code: String,
        @RequestParam("login") login: String,
        @RequestParam("amount") amount: Long,
        @RequestParam("auto-renewable", required = false) autoRenewable: Boolean = false,
    ) : ResponseEntity<MyDealDto>

    @GetMapping("/{code}/sell")
    fun sell(
        @PathVariable("code") code: String,
        @RequestParam("login") login: String,
        @RequestParam("amount") amount: Long,
        @RequestParam("auto-renewable", required = false) autoRenewable: Boolean = false,
    ) : ResponseEntity<MyDealDto>

    @PostMapping("/{code}")
    fun create(
        @PathVariable("code") code: String,
        @RequestParam("name") name: String,
        @RequestParam("price") price: Double,
    ) : ResponseEntity<CompanyDto>

    @PatchMapping("/{code}")
    fun updatePrice(
        @PathVariable("code") code: String,
        @RequestParam("price") price: Double,
    ) : ResponseEntity<CompanyDto>

    @DeleteMapping("/{code}")
    fun delete(
        @PathVariable("code") code: String,
    )
}