package com.github.cannor147.itmo.sd.lab11.dto

import java.time.LocalDateTime

data class ClosedDealDto(
    override val id: String,
    override val created: LocalDateTime,
    override val companyCode: String,
    override val companyName: String,
    override val amount: Long,
    val finalPrice: Double,
    val seller: String,
    val buyer: String,
    val paid: LocalDateTime,
) : DealDto