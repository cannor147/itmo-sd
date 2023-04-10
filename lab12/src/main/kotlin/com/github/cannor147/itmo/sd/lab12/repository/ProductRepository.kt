package com.github.cannor147.itmo.sd.lab12.repository

import com.github.cannor147.itmo.sd.lab12.model.Product
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : CoroutineCrudRepository<Product, Long> {
    suspend fun findAllByCategory(category: String): Flow<Product>
    suspend fun findAllByNameLikeIgnoreCase(name: String): Flow<Product>
    suspend fun findAllByOwnerId(ownerId: Long): Flow<Product>
}