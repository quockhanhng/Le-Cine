package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName

class MovieResponse {

    @SerializedName("results")
    lateinit var movies: List<Movie>

    fun getMovieList() = movies
}
