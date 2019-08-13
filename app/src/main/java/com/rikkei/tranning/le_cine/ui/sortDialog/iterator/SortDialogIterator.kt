package com.rikkei.tranning.le_cine.ui.sortDialog.iterator

import com.rikkei.tranning.le_cine.ui.sortDialog.SortType

interface SortDialogIterator {

    fun getSelectedSortingOption(): Int

    fun setSortingOption(sortType: SortType)
}