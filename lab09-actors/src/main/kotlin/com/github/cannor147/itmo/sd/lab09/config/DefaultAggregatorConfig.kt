package com.github.cannor147.itmo.sd.lab09.config

import com.github.cannor147.itmo.sd.lab09.client.ImdbClient
import com.github.cannor147.itmo.sd.lab09.client.KinopoiskClient
import java.time.Duration

class DefaultAggregatorConfig : AggregatorConfig {
    override val imdbClient: ImdbClient
        get() = TODO("IMDb client is not implemented yet")
    override val kinopoiskClient: KinopoiskClient
        get() = TODO("Kinopoisk client is not implemented yet")
    override val maxSearchCount: Int
        get() = 5
    override val actorTimeout: Duration
        get() = Duration.ofSeconds(3)
}