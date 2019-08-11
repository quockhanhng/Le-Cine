package com.rikkei.tranning.le_cine.ui.listFragment.iterator

import android.util.Log
import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.MovieResponse
import io.reactivex.Observable

class MoviesListIteratorImpl(private var tmdbWebService: TmdbWebService) :
    MoviesListIterator {

    override fun fetchMovies(page: Int): Observable<List<Movie>> {
        Log.d("Log", tmdbWebService.popularMovies(1).map(MovieResponse::getMovieList).toString())
        return tmdbWebService.popularMovies(page).map(MovieResponse::getMovieList)
    }

    override fun searchMovie(searchQuery: String): Observable<List<Movie>> {
        return tmdbWebService.searchMovies(searchQuery).map(MovieResponse::getMovieList)
    }
}