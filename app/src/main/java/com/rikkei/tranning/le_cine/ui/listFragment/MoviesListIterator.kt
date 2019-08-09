package com.rikkei.tranning.le_cine.ui.listFragment

import com.rikkei.tranning.le_cine.model.Movie
import io.reactivex.Observable

interface MoviesListIterator {

    fun fetchMovies(page: Int): Observable<List<Movie>>
    fun searchMovie(searchQuery: String): Observable<List<Movie>>
}