package com.github.cannor147.itmo.sd.lab12.repository

import com.github.cannor147.itmo.sd.lab12.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findAllByCategory(category: String): List<Product>
    fun findAllByNameLikeIgnoreCase(name: String): List<Product>
    fun findAllByOwnerId(ownerId: Long): List<Product>
}