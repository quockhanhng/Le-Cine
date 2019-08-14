package com.rikkei.tranning.le_cine.ui.favourite

import com.rikkei.tranning.le_cine.di.module.AppModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class FavouriteModule {
    @Provides
    @Singleton
    internal fun provideFavouriteIterator(databaseHelper: DatabaseHelper): FavouriteIterator {
        return FavouriteIteratorImpl(databaseHelper)
    }
}