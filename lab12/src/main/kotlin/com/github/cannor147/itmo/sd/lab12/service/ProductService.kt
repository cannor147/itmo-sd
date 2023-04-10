package com.github.cannor147.itmo.sd.lab12.service

import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.dto.ProductDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.stereotype.Service

@Service
interface ProductService {
    suspend fun create(
        name: String,
        category: String,
        image: String,
        amount: Double,
        currency: Currency?,
        ownerId: Long
    ): ProductDto
    suspend fun findByOwner(ownerId: Long): List<ProductDto>
    suspend fun findByCategory(category: String): List<ProductDto>
    suspend fun findById(id: Long): ProductDto
    suspend fun findByName(prefix: String): List<ProductDto>
}