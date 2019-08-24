package com.rikkei.tranning.le_cine.di.component

import com.rikkei.tranning.le_cine.di.module.*
import com.rikkei.tranning.le_cine.ui.favourite.FavouriteModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, FavouriteModule::class])
interface AppComponent {
    fun plus(listModule: ListModule): ListComponent

    fun plus(detailModule: DetailModule): DetailComponent

    fun plus(personDetailModule: PersonDetailModule): PersonDetailComponent
}