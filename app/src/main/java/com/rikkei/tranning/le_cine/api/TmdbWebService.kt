package com.rikkei.tranning.le_cine.api

import com.rikkei.tranning.le_cine.model.GenreResponse
import com.rikkei.tranning.le_cine.model.MovieResponse
import com.rikkei.tranning.le_cine.model.ReviewResponse
import com.rikkei.tranning.le_cine.model.VideoResponse
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbWebService {

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    fun popularMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    fun highestRatedMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("3/discover/movie?language=en&sort_by=release_date.desc")
    fun newestMovies(@Query("release_date.lte") maxReleaseDate: String, @Query("vote_count.gte") minVoteCount: Int): Observable<MovieResponse>

    @GET("3/search/movie?language=en-US&page=1")
    fun searchMovies(@Query("query") searchQuery: String): Observable<MovieResponse>

    @GET("3/movie/{movieId}/videos")
    fun trailers(@Path("movieId") movieId: String): Observable<VideoResponse>

    @GET("3/movie/{movieId}/reviews")
    fun reviews(@Path("movieId") movieId: String): Observable<ReviewResponse>

    @GET("/3/genre/movie/list")
    fun genres(): Observable<GenreResponse>
}
