package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName

class ReviewResponse {

    @SerializedName("results")
    lateinit var reviews: List<Review>

    fun getReviewsList() = reviews
}