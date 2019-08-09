package com.rikkei.tranning.le_cine.di.component

import com.rikkei.tranning.le_cine.di.module.ListModule
import com.rikkei.tranning.le_cine.di.scope.ListingScope
import com.rikkei.tranning.le_cine.ui.listFragment.MovieListFragment
import dagger.Subcomponent

@ListingScope
@Subcomponent(modules = [ListModule::class])
interface ListComponent {
    fun inject(target: MovieListFragment)
}