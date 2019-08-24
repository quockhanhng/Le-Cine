package com.rikkei.tranning.le_cine.di.module

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.ui.personFragment.iterator.PersonDetailIterator
import com.rikkei.tranning.le_cine.ui.personFragment.iterator.PersonDetailIteratorImpl
import com.rikkei.tranning.le_cine.ui.personFragment.presenter.PersonDetailPresenter
import com.rikkei.tranning.le_cine.ui.personFragment.presenter.PersonDetailPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class PersonDetailModule {
    @Provides
    fun providePersonDetailIterator(api: TmdbWebService): PersonDetailIterator {
        return PersonDetailIteratorImpl(api)
    }

    @Provides
    fun providePersonDetailPresenter(personDetailIterator: PersonDetailIterator): PersonDetailPresenter {
        return PersonDetailPresenterImpl(personDetailIterator)
    }
}