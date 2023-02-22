package com.github.cannor147.itmo.sd.lab10.dto

import java.time.Instant

data class SubscriptionDto(
    val creationTime: Instant,
    val validUntilTime: Instant,
    val visitor: VisitorDto,
)
