package com.github.cannor147.itmo.sd.lab12.converter

import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.stereotype.Component

@Component
class PriceConverter {
    fun toDto(amount: Double, currency: Currency) = PriceDto(
        amount = amount,
        currency = currency,
    )
}