package com.github.cannor147.itmo.sd.lab09.actor

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.actor.UntypedAbstractActor
import com.github.cannor147.itmo.sd.lab09.config.AggregatorConfig
import com.github.cannor147.itmo.sd.lab09.dto.*
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class AggregateActor(
    private val aggregatorConfig: AggregatorConfig,
) : UntypedAbstractActor() {
    private val aggregationSender: AtomicReference<ActorRef?> = AtomicReference()
    private var text: AtomicReference<String?> = AtomicReference()
    private val maxAggregationCount = aggregatorConfig.maxSearchCount * SearchEngine.values().size
    private var responses: MutableList<SearchResponse> = Collections.synchronizedList(ArrayList(maxAggregationCount))
    private val counter: AtomicInteger = AtomicInteger()

    override fun onReceive(message: Any?) {
        if (message is AggregateRequest) {
            aggregationSender.set(sender)
            text.set(message.text)
            counter.set(SearchEngine.values().size)

            for (searchEngine in SearchEngine.values()) {
                val childActorRef = context.actorOf(Props.create(SearchActor::class.java, aggregatorConfig))
                childActorRef.tell(SearchRequest(message.text, searchEngine), self())
            }
            context.receiveTimeout = message.timeout
        } else if (message is ReceiveTimeout) {
            finalize()
        } else if (message is List<*>) {
            responses.addAll(message.map { it as SearchResponse }.take(aggregatorConfig.maxSearchCount))
            if (counter.decrementAndGet() == 0) {
                finalize()
            }
        }
    }

    private fun finalize() {
        aggregationSender.get()!!.tell(AggregationResponse(text.get()!!, responses), self())
        context.stop(self())
    }
}