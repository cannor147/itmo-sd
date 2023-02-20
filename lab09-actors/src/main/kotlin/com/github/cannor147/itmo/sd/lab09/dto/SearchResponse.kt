package com.github.cannor147.itmo.sd.lab09.dto

data class SearchResponse(
    val title: String,
    val description: String?,
    val engine: SearchEngine,
)
