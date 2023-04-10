package com.github.cannor147.itmo.sd.lab12.converter

import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.dto.ProductDto
import com.github.cannor147.itmo.sd.lab12.model.Product
import org.springframework.stereotype.Component

@Component
class ProductConverter(
    private val priceConverter: PriceConverter,
) {
    fun toDto(product: Product, originalPrice: PriceDto, userPrice: PriceDto) = ProductDto(
        id = product.id,
        name = product.name!!,
        category = product.category!!,
        image = product.image,
        price = userPrice,
        originalPrice = originalPrice,
        ownerId = product.owner!!.id,
        created = product.created,
    )
}