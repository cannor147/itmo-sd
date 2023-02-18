package com.github.cannor147.itmo.sd.lab09

import com.github.cannor147.itmo.sd.lab09.client.ImdbClient
import com.github.cannor147.itmo.sd.lab09.client.KinopoiskClient

interface AggregatorProxy {
    val imdbClient: ImdbClient
    val kinopoiskClient: KinopoiskClient
}