package com.rikkei.tranning.le_cine.ui.detailFragment.presenter

import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.Review
import com.rikkei.tranning.le_cine.model.Video
import com.rikkei.tranning.le_cine.ui.detailFragment.iterator.MovieDetailIterator
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailView
import com.rikkei.tranning.le_cine.ui.favourite.FavouriteIterator
import com.rikkei.tranning.le_cine.util.unsubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieDetailPresenterImpl(
    private var movieIterator: MovieDetailIterator,
    private var favouriteIterator: FavouriteIterator
) : MovieDetailPresenter {

    private var view: MovieDetailView? = null
    private lateinit var trailersSubscription: Disposable
    private lateinit var reviewSubscription: Disposable


    override fun setView(view: MovieDetailView) {
        this.view = view
    }

    override fun showDetails(movie: Movie) {
        view?.showDetails(movie)
    }

    override fun showTrailers(movie: Movie) {
        trailersSubscription = movieIterator.getTrailers(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onGetTrailersSuccess(it) }, { onGetTrailersFailure() })

    }

    private fun onGetTrailersSuccess(videos: List<Video>) {
        view?.showTrailers(videos)
    }

    private fun onGetTrailersFailure() {
    }

    override fun showReviews(movie: Movie) {
        reviewSubscription = movieIterator.getReviews(movie.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onGetReviewsSuccess(it) }, { onGetReviewsFailure() })
    }

    private fun onGetReviewsSuccess(reviews: List<Review>) {
        view?.showReviews(reviews)
    }

    private fun onGetReviewsFailure() {
    }

    override fun showFavouriteButton(movie: Movie) {
        if (favouriteIterator.isFavorite(movie.id)) {
            view?.showFavourite()
        } else {
            view?.showUnFavourite()
        }
    }

    override fun isFavouriteMovie(movie: Movie): Boolean {
        return favouriteIterator.isFavorite(movie.id)
    }

    override fun onFavouriteClick(movie: Movie) {
        val isFavorite = favouriteIterator.isFavorite(movie.id)
        if (isFavorite) {
            favouriteIterator.unFavorite(movie.id)
            view?.showUnFavourite()
        } else {
            favouriteIterator.setFavorite(movie)
            view?.showFavourite()
        }
    }

    override fun destroy() {
        view = null
        unsubscribe(trailersSubscription, reviewSubscription)
    }
}