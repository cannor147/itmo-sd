package com.github.cannor147.itmo.sd.lab09.config

import com.github.cannor147.itmo.sd.lab09.client.ImdbClient
import com.github.cannor147.itmo.sd.lab09.client.KinopoiskClient
import java.time.Duration

interface AggregatorConfig {
    val imdbClient: ImdbClient
    val kinopoiskClient: KinopoiskClient
    val maxSearchCount: Int
    val actorTimeout: Duration
}