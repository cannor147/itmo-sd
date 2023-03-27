package com.github.cannor147.itmo.sd.lab11.converter

import com.github.cannor147.itmo.sd.lab11.dto.ClosedDealDto
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import com.github.cannor147.itmo.sd.lab11.dto.OpenDealDto
import com.github.cannor147.itmo.sd.lab11.model.Deal
import org.springframework.stereotype.Component

@Component
class DealConverter {
    fun toDealDto(deal: Deal, login: String? = null) = when {
        login != null && deal.seller == login || deal.buyer == login -> toMyDealDto(deal)
        deal.paid == null -> toOpenDealDto(deal)
        else -> toClosedDealDto(deal)
    }

    fun toMyDealDto(deal: Deal) = MyDealDto(
        id = deal.id,
        created = deal.created,
        companyCode = deal.stock?.code!!,
        companyName = deal.stock?.company!!,
        amount = deal.amount!!,
        autoRenewable = deal.autoRenewable,
        price = deal.finalPrice ?: deal.stock?.price!!,
        seller = deal.seller,
        buyer = deal.buyer,
        paid = deal.paid,
        deleted = deal.deleted,
    )

    fun toOpenDealDto(deal: Deal) = OpenDealDto(
        id = deal.id,
        created = deal.created,
        companyCode = deal.stock?.code!!,
        companyName = deal.stock?.company!!,
        amount = deal.amount!!,
        price = deal.stock?.price!!,
        seller = deal.seller,
        buyer = deal.buyer,
    )

    fun toClosedDealDto(deal: Deal) = ClosedDealDto(
        id = deal.id,
        created = deal.created,
        companyCode = deal.stock?.code!!,
        companyName = deal.stock?.company!!,
        amount = deal.amount!!,
        finalPrice = deal.finalPrice!!,
        seller = deal.seller!!,
        buyer = deal.buyer!!,
        paid = deal.paid!!,
    )

    fun require(deal: Deal?): Deal = deal ?: throw NoSuchElementException(NO_SUCH_DEAL_MESSAGE)

    fun requireOpen(deal: Deal?): Deal = deal?.takeIf { it.paid == null && (it.buyer == null || it.seller == null) }
        ?: throw NoSuchElementException(NO_SUCH_DEAL_OR_CLOSED_MESSAGE)

    companion object {
        private const val NO_SUCH_DEAL_MESSAGE = "No such deal"
        private const val NO_SUCH_DEAL_OR_CLOSED_MESSAGE = "No such deal or deal is closed"
    }
}