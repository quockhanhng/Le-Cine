package com.rikkei.tranning.le_cine.model

import android.os.Parcel
import android.os.Parcelable

data class Review(
    val id: String,
    val author: String,
    val content: String,
    var url: String
) : Parcelable {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(url)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }
}