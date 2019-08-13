package com.rikkei.tranning.le_cine.ui.sortDialog.iterator

import com.rikkei.tranning.le_cine.ui.sortDialog.SortType
import com.rikkei.tranning.le_cine.ui.sortDialog.SelectOptionStore

class SortDialogIteratorImpl(private var sortingOption: SelectOptionStore) : SortDialogIterator {

    override fun getSelectedSortingOption(): Int = sortingOption.getSelectedOption()

    override fun setSortingOption(sortType: SortType) {
        sortingOption.setSelectedOption(sortType)
    }
}