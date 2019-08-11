package com.rikkei.tranning.le_cine.di.component

import com.rikkei.tranning.le_cine.di.module.AppModule
import com.rikkei.tranning.le_cine.di.module.DetailModule
import com.rikkei.tranning.le_cine.di.module.ListModule
import com.rikkei.tranning.le_cine.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun plus(listModule: ListModule): ListComponent

    fun plus(detailModule: DetailModule): DetailComponent
}