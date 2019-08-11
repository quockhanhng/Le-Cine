package com.rikkei.tranning.le_cine.ui.listFragment.presenter

import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.listFragment.iterator.MoviesListIterator
import com.rikkei.tranning.le_cine.ui.listFragment.view.MoviesListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class MoviesListPresenterImpl(val iterator: MoviesListIterator) :
    MoviesListPresenter {

    private var view: MoviesListView? = null
    private lateinit var fetchSubscription: Disposable
    private var currentPage = 1
    private var loadedMovies = ArrayList<Movie>()

    override fun firstPage() {
        currentPage = 1
        loadedMovies.clear()
        displayMovies()
    }

    override fun nextPage() {
    }

    override fun setView(view: MoviesListView) {
        this.view = view
        displayMovies()
    }

    private fun displayMovies() {
        showLoading()
        fetchSubscription = iterator.fetchMovies(currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onMovieFetchSuccess, this::onMovieFetchFailed)
    }

    override fun searchMovie(searchText: String) {
    }

    override fun searchMovieBackPressed() {
    }

    override fun destroy() {
    }

    private fun onMovieFetchSuccess(movies: List<Movie>) {
        loadedMovies.addAll(movies)

        if (isViewAttached()) {
            view?.showMovies(loadedMovies)
        }
    }

    private fun onMovieFetchFailed(e: Throwable) {
        e.message?.let { view?.loadingFailed(it) }
    }

    private fun showLoading() {
        if (isViewAttached()) {
            view?.loadingStarted()
        }
    }

    private fun isViewAttached(): Boolean {
        return view != null
    }
}