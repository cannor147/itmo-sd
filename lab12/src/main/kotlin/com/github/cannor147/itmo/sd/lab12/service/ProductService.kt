package com.github.cannor147.itmo.sd.lab12.service

import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.dto.ProductDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.stereotype.Service

@Service
interface ProductService {
    fun create(
        name: String,
        category: String,
        image: String,
        amount: Double,
        currency: Currency?,
        ownerId: Long
    ): ProductDto
    fun findByOwner(ownerId: Long): List<ProductDto>
    fun findByCategory(category: String): List<ProductDto>
    fun findById(id: Long): ProductDto
    fun findByName(prefix: String): List<ProductDto>
}