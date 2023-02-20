package com.github.cannor147.itmo.sd.lab09

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.PatternsCS
import com.github.cannor147.itmo.sd.lab09.actor.AggregateActor
import com.github.cannor147.itmo.sd.lab09.config.ImmutableAggregatorConfig
import com.github.cannor147.itmo.sd.lab09.dto.AggregateRequest
import com.github.cannor147.itmo.sd.lab09.dto.AggregationResponse

class AggregatorProxy(
    private val config: ImmutableAggregatorConfig,
) {
    private val system: ActorSystem = ActorSystem.create("aggregator")

    fun search(text: String): AggregationResponse {
        val actorRef = system.actorOf(Props.create(AggregateActor::class.java, config))
        val result = PatternsCS.ask(actorRef, AggregateRequest(text), config.actorTimeout).toCompletableFuture().join()
        return result as AggregationResponse
    }
}