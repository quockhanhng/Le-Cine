package com.rikkei.tranning.le_cine.ui.sortDialog.presenter

import com.rikkei.tranning.le_cine.ui.sortDialog.SortType
import com.rikkei.tranning.le_cine.ui.sortDialog.iterator.SortDialogIterator
import com.rikkei.tranning.le_cine.ui.sortDialog.view.SortDialogView

class SortDialogPresenterImpl(var iterator: SortDialogIterator) : SortDialogPresenter {

    private var view: SortDialogView? = null

    override fun setView(view: SortDialogView) {
        this.view = view
    }

    override fun setLastSavedOption() {
        view?.let {
            when (iterator.getSelectedSortingOption()) {
                SortType.MOST_POPULAR.value -> it.setPopularChecked()
                SortType.HIGHEST_RATED.value -> it.setHighestRatedChecked()
                else -> it.setNewestChecked()
            }
        }
    }

    override fun onPopularMoviesSelected() {
        view?.let {
            iterator.setSortingOption(SortType.MOST_POPULAR)
            it.dismissDialog()
        }
    }

    override fun onHighestRatedMoviesSelected() {
        view?.let {
            iterator.setSortingOption(SortType.HIGHEST_RATED)
            it.dismissDialog()
        }
    }

    override fun onNewestMoviesSelected() {
        view?.let {
            iterator.setSortingOption(SortType.NEWEST)
            it.dismissDialog()
        }
    }

    override fun destroy() {
        view = null
    }
}