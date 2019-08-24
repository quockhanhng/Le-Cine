package com.rikkei.tranning.le_cine

import android.app.Application
import com.rikkei.tranning.le_cine.di.component.*
import com.rikkei.tranning.le_cine.di.module.*
import com.rikkei.tranning.le_cine.ui.favourite.FavouriteModule

class App : Application() {

    private lateinit var appComponent: AppComponent
    private var listComponent: ListComponent? = null
    private var detailComponent: DetailComponent? = null
    private var personDetailComponent: PersonDetailComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
    }

    @Suppress("DEPRECATION")
    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .appModule(AppModule(this))
            .favouriteModule(FavouriteModule())
            .build()
    }

    fun createListComponent(): ListComponent {
        listComponent = appComponent.plus(ListModule())
        return listComponent as ListComponent
    }

    fun releaseListComponent() {
        listComponent = null
    }

    fun getListComponent(): ListComponent? = listComponent

    fun createDetailComponent(): DetailComponent {
        detailComponent = appComponent.plus(DetailModule())
        return detailComponent as DetailComponent
    }

    fun releaseDetailComponent() {
        detailComponent = null
    }

    fun createPersonDetailComponent(): PersonDetailComponent {
        personDetailComponent = appComponent.plus(PersonDetailModule())
        return personDetailComponent as PersonDetailComponent
    }

    fun releasePersonDetailComponent() {
        personDetailComponent = null
    }
}