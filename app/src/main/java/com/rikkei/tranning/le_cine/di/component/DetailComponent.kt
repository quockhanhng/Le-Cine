package com.rikkei.tranning.le_cine.di.component

import com.rikkei.tranning.le_cine.di.module.DetailModule
import com.rikkei.tranning.le_cine.di.scope.DetailsScope
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailFragment
import dagger.Subcomponent

@DetailsScope
@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {
    fun inject(target: MovieDetailFragment)
}