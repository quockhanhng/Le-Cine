package com.rikkei.tranning.le_cine

import android.app.Application
import com.rikkei.tranning.le_cine.di.component.AppComponent
import com.rikkei.tranning.le_cine.di.component.DaggerAppComponent
import com.rikkei.tranning.le_cine.di.component.DetailComponent
import com.rikkei.tranning.le_cine.di.component.ListComponent
import com.rikkei.tranning.le_cine.di.module.AppModule
import com.rikkei.tranning.le_cine.di.module.DetailModule
import com.rikkei.tranning.le_cine.di.module.ListModule
import com.rikkei.tranning.le_cine.di.module.NetworkModule

class App : Application() {

    private lateinit var appComponent: AppComponent
    private var listComponent: ListComponent? = null
    private var detailComponent: DetailComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
    }

    @Suppress("DEPRECATION")
    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .appModule(AppModule(this))
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
}