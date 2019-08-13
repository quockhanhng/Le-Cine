package com.rikkei.tranning.le_cine.di.module

import com.rikkei.tranning.le_cine.ui.sortDialog.iterator.SortDialogIterator
import com.rikkei.tranning.le_cine.ui.sortDialog.iterator.SortDialogIteratorImpl
import com.rikkei.tranning.le_cine.ui.sortDialog.presenter.SortDialogPresenter
import com.rikkei.tranning.le_cine.ui.sortDialog.presenter.SortDialogPresenterImpl
import com.rikkei.tranning.le_cine.ui.sortDialog.SelectOptionStore
import dagger.Module
import dagger.Provides

@Module
class SortModule {

    @Provides
    fun provideSortDialogIterator(sortingOption: SelectOptionStore): SortDialogIterator {
        return SortDialogIteratorImpl(sortingOption)
    }

    @Provides
    fun provideSortDialogPresenter(iterator: SortDialogIterator): SortDialogPresenter {
        return SortDialogPresenterImpl(iterator)
    }
}