package com.rikkei.tranning.le_cine.ui.listFragment.presenter

import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.listFragment.iterator.MoviesListIterator
import com.rikkei.tranning.le_cine.ui.listFragment.view.MoviesListView
import com.rikkei.tranning.le_cine.util.unsubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class MoviesListPresenterImpl(val iterator: MoviesListIterator) :
    MoviesListPresenter {

    private var view: MoviesListView? = null
    private var currentPage = 1
    private var loadedMovies = ArrayList<Movie>()
    private var fetchSubscription: Disposable? = null
    private var movieSearchSubscription: Disposable? = null
    private var showingSearchResult = false

    override fun firstPage() {
        currentPage = 1
        loadedMovies.clear()
        displayMovies()
    }

    override fun nextPage() {
        if (showingSearchResult)
            return
        if (iterator.isMoviesLoadFromFavourites()) {
            currentPage++
            displayMovies()
        }
    }

    override fun setView(view: MoviesListView) {
        this.view = view
        if (!showingSearchResult)
            displayMovies()
    }

    private fun displayMovies() {
        showLoading()
        fetchSubscription = iterator.fetchMovies(currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onMovieFetchSuccess, this::onMovieFetchFailed)
    }

    private fun displayMovieSearchResult(searchText: String) {
        showingSearchResult = true
        showLoading()
        movieSearchSubscription = iterator.searchMovie(searchText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onMovieSearchSuccess(it) }, { onMovieSearchFailed(it) })
    }

    override fun searchMovie(searchText: String) {
        if (searchText.isEmpty()) {
            displayMovies()
        } else {
            displayMovieSearchResult(searchText)
        }
    }

    private fun onMovieSearchSuccess(movies: List<Movie>) {
        loadedMovies.clear()
        loadedMovies = ArrayList(movies)

        view?.showMovies(loadedMovies)

    }

    private fun onMovieSearchFailed(e: Throwable) {
        e.message?.let { view?.loadingFailed(it) }
    }

    override fun searchMovieBackPressed() {
        if (showingSearchResult) {
            showingSearchResult = false
            loadedMovies.clear()
            displayMovies()
        }
    }

    override fun destroy() {
        view = null
        unsubscribe(fetchSubscription, movieSearchSubscription)
    }

    private fun onMovieFetchSuccess(movies: List<Movie>) {
        if (iterator.isMoviesLoadFromFavourites())
            loadedMovies.addAll(movies)
        else
            loadedMovies = ArrayList(movies)
        view?.showMovies(loadedMovies)
    }

    private fun onMovieFetchFailed(e: Throwable) {
        e.message?.let { view?.loadingFailed(it) }
    }

    private fun showLoading() {
        view?.loadingStarted()
    }
}