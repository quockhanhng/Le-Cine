package com.rikkei.tranning.le_cine.di.module

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.di.scope.ListingScope
import com.rikkei.tranning.le_cine.ui.listFragment.MoviesListIterator
import com.rikkei.tranning.le_cine.ui.listFragment.MoviesListIteratorImpl
import com.rikkei.tranning.le_cine.ui.listFragment.MoviesListPresenter
import com.rikkei.tranning.le_cine.ui.listFragment.MoviesListPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class ListModule {
    @Provides
    fun provideMoviesListPresenter(moviesListIterator: MoviesListIterator): MoviesListPresenter {
        return MoviesListPresenterImpl(moviesListIterator)
    }

    @Provides
    @ListingScope
    fun provideMoviesListIterator(api: TmdbWebService): MoviesListIterator {
        return MoviesListIteratorImpl(api)
    }
}