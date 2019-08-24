package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName

data class Person(
    val id: String,
    val name: String,
    @SerializedName("also_known_as") val alsoKnowAs: List<String>,
    val birthday: String?,
    @SerializedName("deathday") val deathDay: String?,
    val gender: Int,
    val biography: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("place_of_birth") val birthPlace: String?,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("homepage") val homePage: String?
) {

    fun getProfileUrl(): String {
        return "http://image.tmdb.org/t/p/w342$profilePath"
    }
}