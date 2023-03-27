package com.github.cannor147.itmo.sd.lab11.service


import com.github.cannor147.itmo.sd.lab11.converter.CompanyConverter
import com.github.cannor147.itmo.sd.lab11.dto.CompanyDto
import com.github.cannor147.itmo.sd.lab11.model.Stock
import com.github.cannor147.itmo.sd.lab11.repository.StockRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val stockRepository: StockRepository,
    private val companyConverter: CompanyConverter,
) {
    @Transactional
    fun get(code: String): CompanyDto = stockRepository.findByCodeAndDeletedFalse(code)
        .let(companyConverter::require)
        .let(companyConverter::toCompanyDto)

    @Transactional
    fun search(name: String): List<CompanyDto> = companyConverter.toCompanyDto(
        stockRepository.searchAllByCompanyLikeIgnoreCaseAndDeletedFalse("$name%")
    )

    @Transactional
    fun getAll(): List<CompanyDto> = companyConverter.toCompanyDto(
        stockRepository.findAllByDeletedFalse()
    )

    fun create(code: String, name: String, price: Double): CompanyDto = try {
        companyConverter.toCompanyDto(
            stockRepository.save(Stock(code = code, company = name, price = price)),
            emptyList()
        )
    } catch (e: Exception) {
        throw IllegalArgumentException("Can't create company with code $code and name '$name'")
    }

    @Transactional
    fun updatePrice(code: String, price: Double): CompanyDto {
        if (price < 0) {
            throw IllegalArgumentException("Price can't be less than zero")
        }

        val stock = companyConverter.require(stockRepository.findByCodeAndDeletedFalse(code))
        stock.price = price
        return companyConverter.toCompanyDto(stockRepository.save(stock))
    }

    @Transactional
    fun delete(code: String) {
        val stock = companyConverter.require(stockRepository.findByCodeAndDeletedFalse(code))
        stock.deleted = true
        stockRepository.save(stock)
    }
}

