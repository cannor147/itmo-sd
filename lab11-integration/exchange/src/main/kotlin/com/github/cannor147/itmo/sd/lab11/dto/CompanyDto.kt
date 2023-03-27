package com.github.cannor147.itmo.sd.lab11.dto

data class CompanyDto(
    val code: String,
    val name: String,
    val stockPrice: Double,
    val stockAmount: Long,
)