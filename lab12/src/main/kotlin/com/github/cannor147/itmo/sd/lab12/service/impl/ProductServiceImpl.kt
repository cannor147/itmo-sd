package com.github.cannor147.itmo.sd.lab12.service.impl

import com.github.cannor147.itmo.sd.lab12.converter.PriceConverter
import com.github.cannor147.itmo.sd.lab12.converter.ProductConverter
import com.github.cannor147.itmo.sd.lab12.dto.ProductDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.model.Product
import com.github.cannor147.itmo.sd.lab12.repository.ProductRepository
import com.github.cannor147.itmo.sd.lab12.repository.UserRepository
import com.github.cannor147.itmo.sd.lab12.service.CurrencyService
import com.github.cannor147.itmo.sd.lab12.service.ProductService
import jakarta.transaction.Transactional
import kotlinx.coroutines.flow.toList

class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val productConverter: ProductConverter,
    private val priceConverter: PriceConverter,
    private val currencyService: CurrencyService,
) : ProductService {
    @ExperimentalStdlibApi
    override suspend fun create(
        name: String,
        category: String,
        image: String,
        amount: Double,
        currency: Currency?,
        ownerId: Long
    ): ProductDto {
        val user = userRepository.findById(ownerId) ?: throw IllegalAccessException("You are not authorized")
        val productCurrenct = currency ?: user.currency
        val product = Product(
            name = name,
            category = category,
            image = image,
            number = 1,
            price = amount,
            currency = productCurrenct,
            owner = user,
        )
        return productRepository.save(product).let { this.convert(it) }
    }

    @Transactional
    override suspend fun findByOwner(ownerId: Long) = productRepository.findAllByOwnerId(ownerId)
        .toList()
        .let { this.convert(it) }

    override suspend fun findByCategory(category: String) = productRepository.findAllByCategory(category)
        .toList()
        .let { this.convert(it) }

    @ExperimentalStdlibApi
    @Transactional
    override suspend fun findById(id: Long) = productRepository.findById(id)
        ?.let { this.convert(it) }
        ?: throw NoSuchElementException("No such product")

    override suspend fun findByName(prefix: String) = productRepository.findAllByNameLikeIgnoreCase("$prefix%")
        .toList()
        .let { this.convert(it) }

    private suspend fun convert(products: List<Product>) = products.groupBy(Product::owner)
        .map { (user, products) ->
            val originalPrices = products.map { priceConverter.toDto(it.price!!, it.currency!!) }
            val userPrices = currencyService.convert(originalPrices, user!!.currency!!)
            products.mapIndexed { i, product -> productConverter.toDto(product, originalPrices[i], userPrices[i]) }
        }
        .flatten()

    private suspend fun convert(product: Product): ProductDto {
        val originalPrice = priceConverter.toDto(product.price!!, product.currency!!)
        val userPrice = currencyService.convert(originalPrice, product.owner!!.currency!!)
        return productConverter.toDto(product, originalPrice, userPrice)
    }
}