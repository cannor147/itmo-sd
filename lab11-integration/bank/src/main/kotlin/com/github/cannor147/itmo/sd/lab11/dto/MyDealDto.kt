package com.github.cannor147.itmo.sd.lab11.dto

import java.time.LocalDateTime

data class MyDealDto(
    val id: String,
    val created: LocalDateTime,
    val companyCode: String,
    val companyName: String,
    val amount: Long,
    val autoRenewable: Boolean,
    val price: Double,
    val seller: String?,
    val buyer: String?,
    val paid: LocalDateTime?,
    val deleted: Boolean,
)
