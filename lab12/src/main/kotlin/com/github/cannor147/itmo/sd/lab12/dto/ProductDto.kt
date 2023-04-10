package com.github.cannor147.itmo.sd.lab12.dto

import java.util.*

data class ProductDto(
    val id: Long,
    var name: String,
    var category: String,
    var image: String?,
    var price: PriceDto,
    var originalPrice: PriceDto,
    var ownerId: Long,
    var created: Date,
)
