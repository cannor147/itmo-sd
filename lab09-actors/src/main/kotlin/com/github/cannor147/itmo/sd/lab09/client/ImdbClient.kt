package com.github.cannor147.itmo.sd.lab09.client

import java.io.IOException
import java.util.Locale

interface ImdbClient {
    @Throws(IOException::class)
    fun search(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchTitle(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchMovie(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchSeries(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchName(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchEpisode(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchCompany(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchKeyword(expression: String, locale: Locale? = null): SearchData
    @Throws(IOException::class)
    fun searchAll(expression: String, locale: Locale? = null): SearchData

    @Throws(IOException::class)
    fun search(expression: String, searchType: SearchType, locale: Locale? = null) = when (searchType) {
        SearchType.TITLE -> searchTitle(expression, locale)
        SearchType.MOVIE -> searchMovie(expression, locale)
        SearchType.SERIES -> searchSeries(expression, locale)
        SearchType.NAME -> searchName(expression, locale)
        SearchType.EPISODE -> searchEpisode(expression, locale)
        SearchType.COMPANY -> searchCompany(expression, locale)
        SearchType.KEYWORD -> searchKeyword(expression, locale)
        SearchType.ALL -> searchAll(expression, locale)
    }
}

data class SearchData(
    var searchType: SearchType,
    var expression: String,
    var results: List<SearchResult>,
    var errorMessage: String? = null,
)

data class SearchResult(
    var id: String,
    var resultType: String,
    var image: String?,
    var title: String,
    var description: String?,
)

enum class SearchType {
    TITLE,
    MOVIE,
    SERIES,
    NAME,
    EPISODE,
    COMPANY,
    KEYWORD,
    ALL,
}