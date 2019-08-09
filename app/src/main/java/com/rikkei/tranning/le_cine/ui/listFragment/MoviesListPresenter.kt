package com.rikkei.tranning.le_cine.ui.listFragment

interface MoviesListPresenter {

    fun firstPage()

    fun nextPage()

    fun setView(view: MoviesListView)

    fun searchMovie(searchText: String)

    fun searchMovieBackPressed()

    fun destroy()
}