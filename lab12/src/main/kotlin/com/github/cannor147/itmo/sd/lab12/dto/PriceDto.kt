package com.github.cannor147.itmo.sd.lab12.dto

import com.github.cannor147.itmo.sd.lab12.model.Currency

data class PriceDto(
    val amount: Double,
    val currency: Currency,
)
