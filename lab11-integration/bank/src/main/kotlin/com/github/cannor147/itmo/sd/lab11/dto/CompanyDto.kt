package com.github.cannor147.itmo.sd.lab11.dto

data class CompanyDto(
    val code: String,
    val name: String? = null,
    val stockPrice: Double,
    val stockAmount: Long,

    var myAmount: Long = 0,
    var myHold: Long = 0,
    var openBuyingDeals: Long = 0,
    var openSellingDeals: Long = 0,
    var deals: MutableList<DealDto> = mutableListOf()
)