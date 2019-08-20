package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName


class GenreResponse {

    @SerializedName("genres")
    lateinit var genres: List<Genre>

    fun getGenresList() = genres
}