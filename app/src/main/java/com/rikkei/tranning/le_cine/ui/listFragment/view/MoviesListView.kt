package com.rikkei.tranning.le_cine.ui.listFragment.view

import com.rikkei.tranning.le_cine.model.Movie

interface MoviesListView {
    fun showMovies(movies: List<Movie>?)

    fun loadingStarted()

    fun loadingFailed(errorMessage: String)

    fun onMovieClicked(movie: Movie)
}