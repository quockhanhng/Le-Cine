package com.rikkei.tranning.le_cine.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double = 0.toDouble()
) : Parcelable {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeString(title)
        parcel.writeDouble(voteAverage)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

    fun getPosterUrl(): String {
        return "http://image.tmdb.org/t/p/w342$posterPath"
    }

    fun getBackDropPath(): String {
        return "http://image.tmdb.org/t/p/w780$backdropPath"
    }
}