package com.github.cannor147.itmo.sd.lab12.service.impl

import com.github.cannor147.itmo.sd.lab12.converter.PriceConverter
import com.github.cannor147.itmo.sd.lab12.dto.PriceDto
import com.github.cannor147.itmo.sd.lab12.model.Currency
import com.github.cannor147.itmo.sd.lab12.repository.CourseRepository
import com.github.cannor147.itmo.sd.lab12.service.CurrencyService

class CurrencyServiceImpl(
    private val courseRepository: CourseRepository,
    private val priceConverter: PriceConverter,
) : CurrencyService {
    override fun convert(fromPrices: List<PriceDto>, toCurrency: Currency): List<PriceDto> {
        val fromCurrencies = fromPrices.map(PriceDto::currency).filterNot(toCurrency::equals).toList()
        val currencyToPriceMap = courseRepository.findAllByFromInAndTo(fromCurrencies, toCurrency)
            .associate { it.from!! to it.price!! }
        return fromPrices.map {
            when (it.currency) {
                toCurrency -> it
                in currencyToPriceMap -> priceConverter.toDto(it.amount / currencyToPriceMap[it.currency]!!, toCurrency)
                else -> throw NoSuchElementException("Can't find course for ${it.currency}->$toCurrency")
            }
        }
    }
}
