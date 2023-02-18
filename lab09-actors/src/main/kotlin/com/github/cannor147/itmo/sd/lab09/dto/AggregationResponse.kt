package com.github.cannor147.itmo.sd.lab09.dto

data class AggregationResponse(
    val expression: String,
    val responses: List<SearchResponse>,
)
