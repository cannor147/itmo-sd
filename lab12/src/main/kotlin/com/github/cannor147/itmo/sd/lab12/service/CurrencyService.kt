package com.github.cannor147.itmo.sd.lab12.service

import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import org.springframework.stereotype.Service

@Service
interface CurrencyService {
    suspend fun convert(fromPrice: PriceDto, toCurrency: Currency) = convert(listOf(fromPrice), toCurrency).first()
    suspend fun convert(fromPrices: List<PriceDto>, toCurrency: Currency): List<PriceDto>
}