package com.github.cannor147.itmo.sd.lab09.actor

import akka.actor.UntypedAbstractActor
import com.github.cannor147.itmo.sd.lab09.AggregatorProxy
import com.github.cannor147.itmo.sd.lab09.dto.SearchEngine
import com.github.cannor147.itmo.sd.lab09.dto.SearchRequest
import com.github.cannor147.itmo.sd.lab09.dto.SearchResponse

class SearchActor(
    private val aggregatorProxy: AggregatorProxy,
) : UntypedAbstractActor() {
    private val kinopoiskClient get() = aggregatorProxy.kinopoiskClient
    private val imdbClient get() = aggregatorProxy.imdbClient

    override fun onReceive(message: Any?) {
        if (message is SearchRequest) {
            val searchResponses = when (val engine = message.engine) {
                SearchEngine.IMDB -> imdbClient.searchTitle(message.text)
                    .results
                    .map { SearchResponse(it.title, it.description, engine) }

                SearchEngine.KINOPOISK -> kinopoiskClient.searchByName(message.text)
                    .docs
                    .map { SearchResponse(it.name, it.description, engine) }
            }
            sender.tell(searchResponses, self())
            context.stop(self())
        }
    }
}