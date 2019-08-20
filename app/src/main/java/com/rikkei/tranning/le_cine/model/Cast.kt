package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id") val castId: String,
    val character: String,
    @SerializedName("credit_id") val creditId: String,
    val id: String,
    val name: String,
    @SerializedName("profile_path") val profilePath: String?
) {

    fun getProfileUrl(): String {
        return "http://image.tmdb.org/t/p/w342/$profilePath"
    }
}