package com.github.cannor147.itmo.sd.lab09

import com.github.cannor147.itmo.sd.lab09.client.*
import com.github.cannor147.itmo.sd.lab09.config.AggregatorConfig
import com.github.cannor147.itmo.sd.lab09.config.DefaultAggregatorConfig
import com.github.cannor147.itmo.sd.lab09.config.ImmutableAggregatorConfig
import org.junit.jupiter.api.Assertions
import java.io.IOException
import java.util.*
import kotlin.test.Test

class AggregatorProxyTest {
    private val testConfig: AggregatorConfig = object : DefaultAggregatorConfig() {
        override val imdbClient: ImdbClient
            get() = object : ImdbClient {
                override fun searchTitle(expression: String, locale: Locale?): SearchData = when (expression) {
                    "Pus" -> {
                        Thread.sleep(3000)
                        SearchData(
                            SearchType.TITLE, expression, listOf(
                                SearchResult("t123", "movie", null, "Puss in Boots", null),
                                SearchResult("t456", "movie", null, "Puss in Boots 2", null),
                            )
                        )
                    }

                    "Knight" -> SearchData(
                        SearchType.TITLE, expression, listOf(
                            SearchResult("t200", "movie", null, "The Dark Knight", null),
                            SearchResult("t300", "movie", null, "The Dark Knight Rises", null),
                        )
                    )

                    "Форсаж" -> SearchData(
                        SearchType.TITLE, expression, listOf(
                            SearchResult("t401", "movie", null, "The Fast and the Furious", null),
                            SearchResult("t402", "movie", null, "2 Fast 2 Furious", null),
                            SearchResult("t403", "movie", null, "The Fast and the Furious: Tokyo Drift", null),
                            SearchResult("t404", "movie", null, "Fast & Furious", null),
                            SearchResult("t405", "movie", null, "Fast Five", null),
                            SearchResult("t406", "movie", null, "Fast & Furious 6", null),
                            SearchResult("t407", "movie", null, "Furious 7", null),
                            SearchResult("t408", "movie", null, "The Fate of the Furious", null),
                            SearchResult("t409", "movie", null, "F9", null),
                            SearchResult("t410", "movie", null, "Fast X", null),
                        )
                    )

                    "Zootopia" -> throw IOException("Connection is broken")

                    else -> SearchData(SearchType.TITLE, expression, emptyList())
                }

                override fun search(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'search' is not implemented yet for IMDb client")

                override fun searchMovie(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchMovie' is not implemented yet for IMDb client")

                override fun searchSeries(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchSeries' is not implemented yet for IMDb client")

                override fun searchName(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchName' is not implemented yet for IMDb client")

                override fun searchEpisode(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchEpisode' is not implemented yet for IMDb client")

                override fun searchCompany(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchCompany' is not implemented yet for IMDb client")

                override fun searchKeyword(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchKeyword' is not implemented yet for IMDb client")

                override fun searchAll(expression: String, locale: Locale?): SearchData =
                    TODO("Endpoint 'searchAll' is not implemented yet for IMDb client")

            }
        override val kinopoiskClient: KinopoiskClient
            get() = object : KinopoiskClient {
                override fun searchById(id: Long): Movie =
                    TODO("Endpoint 'searchAll' is not implemented yet for IMDb client")

                override fun searchByImdbId(id: Long): Movie =
                    TODO("Endpoint 'searchAll' is not implemented yet for IMDb client")

                override fun searchByName(name: String, isStrict: Boolean): MovieList = when (name) {
                    "Pus" -> MovieList(
                        listOf(
                            Movie(
                                10,
                                "Пушкин: Последняя дуэль",
                                null,
                                null,
                                MovieType.MOVIE,
                                null,
                                null,
                                2006,
                                null,
                                null
                            ),
                        )
                    )

                    "Knight", "Мушкетёр" -> MovieList(
                        listOf(
                            Movie(51, "Три Мушкетёра", null, null, MovieType.MOVIE, null, null, 1979, null, null),
                        )
                    )

                    "Форсаж" -> MovieList(
                        listOf(
                            Movie(61, "Форсаж 1", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(62, "Форсаж 2", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(63, "Форсаж 3", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(64, "Форсаж 4", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(65, "Форсаж 5", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(66, "Форсаж 6", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(67, "Форсаж 7", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(68, "Форсаж 8", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(69, "Форсаж 9", null, null, MovieType.MOVIE, null, null, null, null, null),
                            Movie(60, "Форсаж 10", null, null, MovieType.MOVIE, null, null, null, null, null),
                        )
                    )

                    "Zootopia" -> MovieList(
                        listOf(
                            Movie(100, "Зверополис", null, null, MovieType.CARTOON, null, null, null, null, null),
                        )
                    )

                    else -> MovieList(emptyList())
                }
            }
    }
    private val aggregatorProxy: AggregatorProxy = AggregatorProxy(ImmutableAggregatorConfig(testConfig))

    @Test
    fun testEmpty() {
        Assertions.assertEquals(0, aggregatorProxy.search("Asdfnbkefg").responses.size)
    }

    @Test
    fun testSingle() {
        Assertions.assertEquals(1, aggregatorProxy.search("Мушкетёр").responses.size)
    }

    @Test
    fun testBoth() {
        Assertions.assertEquals(3, aggregatorProxy.search("Knight").responses.size)
    }

    @Test
    fun testTimeout() {
        Assertions.assertEquals(1, aggregatorProxy.search("Pus").responses.size)
    }

    @Test
    fun testLimit() {
        Assertions.assertEquals(10, aggregatorProxy.search("Форсаж").responses.size)
    }

    @Test
    fun testException() {
        Assertions.assertEquals(1, aggregatorProxy.search("Zootopia").responses.size)
    }
}