package com.github.cannor147.itmo.sd.lab12.controller

import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.service.CurrencyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/currency"])
class CurrencyController(
    private val currencyService: CurrencyService,
) {
    @PostMapping(value = ["/list"])
    fun convert(
        @RequestParam(name = "amount") fromAmount: Double,
        @RequestParam(name = "currency") fromCurrency: Currency,
        @RequestParam(name = "to") toCurrency: Currency,
    ) = currencyService.convert(PriceDto(fromAmount, toCurrency), toCurrency).let { ResponseEntity.ok(it) }
}