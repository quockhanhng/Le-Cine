package com.rikkei.tranning.le_cine.di.module

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.di.scope.ListingScope
import com.rikkei.tranning.le_cine.ui.listFragment.iterator.MoviesListIterator
import com.rikkei.tranning.le_cine.ui.listFragment.iterator.MoviesListIteratorImpl
import com.rikkei.tranning.le_cine.ui.listFragment.presenter.MoviesListPresenter
import com.rikkei.tranning.le_cine.ui.listFragment.presenter.MoviesListPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class ListModule {
    @Provides
    @ListingScope
    fun provideMoviesListPresenter(moviesListIterator: MoviesListIterator): MoviesListPresenter {
        return MoviesListPresenterImpl(moviesListIterator)
    }

    @Provides
    @ListingScope
    fun provideMoviesListIterator(api: TmdbWebService): MoviesListIterator {
        return MoviesListIteratorImpl(api)
    }
}