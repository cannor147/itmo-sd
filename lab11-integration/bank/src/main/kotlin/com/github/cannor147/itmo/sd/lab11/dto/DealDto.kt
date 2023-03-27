package com.github.cannor147.itmo.sd.lab11.dto

import java.time.LocalDateTime

data class DealDto(
    val id: String,
    val created: LocalDateTime,
    val companyCode: String,
    val companyName: String,
    val amount: Long,
    val seller: String?,
    val price: Double? = null,
)