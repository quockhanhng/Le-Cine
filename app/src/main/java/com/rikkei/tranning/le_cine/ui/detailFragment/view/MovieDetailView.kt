package com.rikkei.tranning.le_cine.ui.detailFragment.view

import com.rikkei.tranning.le_cine.model.Cast
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.Review
import com.rikkei.tranning.le_cine.model.Video

interface MovieDetailView {

    fun showDetails(movie: Movie)

    fun showTrailers(trailers: List<Video>)

    fun showReviews(reviews: List<Review>)

    fun showCasts(casts: List<Cast>)

    fun showFavourite()

    fun showUnFavourite()
}