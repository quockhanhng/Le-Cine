package com.rikkei.tranning.le_cine.ui.sortDialog.presenter

import com.rikkei.tranning.le_cine.ui.sortDialog.view.SortDialogView

interface SortDialogPresenter {

    fun setView(view: SortDialogView)

    fun setLastSavedOption()

    fun onPopularMoviesSelected()

    fun onHighestRatedMoviesSelected()

    fun onNewestMoviesSelected()

    fun onFavouriteMoviesSelected()

    fun destroy()
}