package com.rikkei.tranning.le_cine.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: String,
    val name: String
) : Parcelable {

    fun findGenreName(id: String) {}

}