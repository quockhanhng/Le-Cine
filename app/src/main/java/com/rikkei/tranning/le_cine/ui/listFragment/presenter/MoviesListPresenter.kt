package com.rikkei.tranning.le_cine.ui.listFragment.presenter

import com.rikkei.tranning.le_cine.ui.listFragment.view.MoviesListView

interface MoviesListPresenter {

    fun firstPage()

    fun nextPage()

    fun setView(view: MoviesListView)

    fun searchMovie(searchText: String)

    fun searchMovieBackPressed()

    fun destroy()
}