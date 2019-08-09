package com.rikkei.tranning.le_cine.model

import com.squareup.moshi.Json

class MovieRespone {

    @Json(name = "results")
    var movies: List<Movie>? = null

    fun getMovieList(): List<Movie> {
        return movies!!
    }
}
