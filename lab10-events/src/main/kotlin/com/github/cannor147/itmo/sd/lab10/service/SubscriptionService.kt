package com.github.cannor147.itmo.sd.lab10.service

import com.github.cannor147.itmo.sd.lab10.dto.SubscriptionDto
import com.github.cannor147.itmo.sd.lab10.dto.VisitorDto
import org.springframework.stereotype.Service

@Service
interface SubscriptionService {
    fun create(phoneNumber: VisitorDto): SubscriptionDto?
    fun renew(phoneNumber: String): SubscriptionDto?
    fun get(phoneNumber: String): SubscriptionDto?
}