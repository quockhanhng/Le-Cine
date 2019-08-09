package com.rikkei.tranning.le_cine.ui.listFragment

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.MovieRespone
import io.reactivex.Observable

class MoviesListIteratorImpl(private var tmdbWebService: TmdbWebService) :
    MoviesListIterator {

    override fun fetchMovies(page: Int): Observable<List<Movie>> {
        return tmdbWebService.popularMovies(page).map(MovieRespone::getMovieList)
    }

    override fun searchMovie(searchQuery: String): Observable<List<Movie>> {
        return tmdbWebService.searchMovies(searchQuery).map(MovieRespone::getMovieList)
    }
}