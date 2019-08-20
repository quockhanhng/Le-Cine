package com.rikkei.tranning.le_cine.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val title: String,
    @SerializedName("genre_ids") val genreIds: List<String>?,
    @SerializedName("vote_average") val voteAverage: Double = 0.toDouble(),
    var genresString: String?
) : Parcelable {

    companion object {

        fun newMovie(
            id: String,
            overview: String,
            releaseDate: String,
            posterPath: String,
            backdropPath: String,
            title: String,
            genreIds: List<String>,
            voteAverage: Double,
            genresString: String
        ): Movie {
            return Movie(id, overview, releaseDate, posterPath, backdropPath, title, genreIds, voteAverage,genresString)
        }
    }

    fun getPosterUrl(): String {
        return "http://image.tmdb.org/t/p/w342$posterPath"
    }

    fun getBackDropPath(): String {
        return "http://image.tmdb.org/t/p/w780$backdropPath"
    }

    fun getGenres(genresList: List<Genre>?): String {
        var result = ""
        if (genreIds != null) {
            for (genreId in genreIds) {
                for (genre in genresList!!) {
                    if (genre.id == genreId)
                        result = result + genre.name + ", "
                }
            }
        }
        result = result.substring(0, result.length - 2)
        return result
    }
}