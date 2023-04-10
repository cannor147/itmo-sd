package com.github.cannor147.itmo.sd.lab12.controller

import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/product"])
class ProductController(
    private val productService: ProductService,
) {
    @PostMapping(value = ["/create"])
    suspend fun create(
        @RequestParam(name = "name") name: String,
        @RequestParam(name = "category") category: String,
        @RequestParam(name = "image") image: String,
        @RequestParam(name = "amount") amount: Double,
        @RequestParam(name = "currency", required = false) currency: Currency?,
        @RequestParam(name = "user_id") userId: Long,
    ) = productService.create(name, category, image, amount, currency, userId).let { ResponseEntity.ok(it) }

    @PostMapping(value = ["/list"])
    suspend fun list(
        @RequestParam(name = "category") category: String,
    ) = productService.findByCategory(category).let { ResponseEntity.ok(it) }

    @PostMapping(value = ["/my"])
    suspend fun list(
        @RequestParam(name = "user_id") userId: Long,
    ) = productService.findById(userId).let { ResponseEntity.ok(it) }

    @PostMapping(value = ["/search"])
    suspend fun search(
        @RequestParam(name = "pattern") pattern: String,
    ) = productService.findByName(pattern).let { ResponseEntity.ok(it) }

    @PostMapping(value = ["/{id}"])
    suspend fun search(
        @PathVariable(name = "id") id: Long,
    ) = productService.findById(id).let { ResponseEntity.ok(it) }
}