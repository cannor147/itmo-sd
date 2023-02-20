package com.github.cannor147.itmo.sd.lab09.dto

data class SearchRequest(
    val text: String,
    val engine: SearchEngine,
)
