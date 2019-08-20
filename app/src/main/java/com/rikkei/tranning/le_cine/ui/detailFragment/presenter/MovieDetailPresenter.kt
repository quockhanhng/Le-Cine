package com.rikkei.tranning.le_cine.ui.detailFragment.presenter

import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailView

interface MovieDetailPresenter {

    fun setView(view: MovieDetailView)

    fun showDetails(movie: Movie)

    fun showTrailers(movie: Movie)

    fun showReviews(movie: Movie)

    fun showCast(movie: Movie)

    fun showFavouriteButton(movie: Movie)

    fun isFavouriteMovie(movie: Movie) : Boolean

    fun onFavouriteClick(movie: Movie)

    fun destroy()
}