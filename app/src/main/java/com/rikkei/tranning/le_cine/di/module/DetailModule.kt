package com.rikkei.tranning.le_cine.di.module

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.ui.detailFragment.iterator.MovieDetailIterator
import com.rikkei.tranning.le_cine.ui.detailFragment.iterator.MovieDetailIteratorImpl
import com.rikkei.tranning.le_cine.ui.detailFragment.presenter.MovieDetailPresenter
import com.rikkei.tranning.le_cine.ui.detailFragment.presenter.MovieDetailPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @Provides
    fun provideMovieDetailIterator(api: TmdbWebService): MovieDetailIterator {
        return MovieDetailIteratorImpl(api)
    }

    @Provides
    fun provideMovieDetailPresenter(movieDetailIterator: MovieDetailIterator): MovieDetailPresenter {
        return MovieDetailPresenterImpl(movieDetailIterator)
    }
}