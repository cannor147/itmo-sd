package com.github.cannor147.itmo.sd.lab09.client

import java.io.IOException

interface KinopoiskClient {
    @Throws(IOException::class)
    fun searchById(id: Long): Movie
    @Throws(IOException::class)
    fun searchByImdbId(id: Long): Movie
    @Throws(IOException::class)
    fun searchByName(name: String, isStrict: Boolean = true): MovieList
}

data class Movie(
    var id: Long,
    var name: String,
    var alternativeName: String,
    var enName: String,
    var type: MovieType,
    var description: String,
    var slogan: String,
    var year: Int,
    var movieLength: Int,
    var status: String,
)

data class MovieList(
    var docs: List<Movie>,
    var total: Long,
    var limit: Long,
    var page: Long,
    var pages: Long,
)

enum class MovieType {
    MOVIE,
    TV_SERIES,
    CARTOON,
    ANIME,
    ANIMATED_SERIES,
    TV_SHOW,
}