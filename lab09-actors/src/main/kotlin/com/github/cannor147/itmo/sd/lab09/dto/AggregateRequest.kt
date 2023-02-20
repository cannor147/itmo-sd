package com.github.cannor147.itmo.sd.lab09.dto

import java.time.Duration

data class AggregateRequest(
    val text: String,
    val timeout: Duration = Duration.ofSeconds(1)
)
