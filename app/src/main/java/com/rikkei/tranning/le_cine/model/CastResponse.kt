package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName

class CastResponse {
    @SerializedName("cast")
    lateinit var casts: List<Cast>

    fun getCastsList() = casts
}