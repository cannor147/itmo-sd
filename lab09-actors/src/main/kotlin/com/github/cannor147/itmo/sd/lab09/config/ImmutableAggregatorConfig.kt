package com.github.cannor147.itmo.sd.lab09.config

import com.github.cannor147.itmo.sd.lab09.client.ImdbClient
import com.github.cannor147.itmo.sd.lab09.client.KinopoiskClient
import java.time.Duration

class ImmutableAggregatorConfig(
    initialConfig: AggregatorConfig,
) : AggregatorConfig {
    override val imdbClient: ImdbClient = initialConfig.imdbClient
    override val kinopoiskClient: KinopoiskClient = initialConfig.kinopoiskClient
    override val maxSearchCount: Int = initialConfig.maxSearchCount
    override val actorTimeout: Duration = initialConfig.actorTimeout
}