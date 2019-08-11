package com.rikkei.tranning.le_cine.model

import com.google.gson.annotations.SerializedName

class VideoResponse {

    @SerializedName("results")
    lateinit var videos: List<Video>

    fun getVideosList() = videos
}