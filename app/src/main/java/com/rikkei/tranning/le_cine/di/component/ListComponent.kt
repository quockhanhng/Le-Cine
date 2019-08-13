package com.rikkei.tranning.le_cine.di.component

import com.rikkei.tranning.le_cine.di.module.ListModule
import com.rikkei.tranning.le_cine.di.module.SortModule
import com.rikkei.tranning.le_cine.di.scope.ListingScope
import com.rikkei.tranning.le_cine.ui.listFragment.view.MovieListFragment
import com.rikkei.tranning.le_cine.ui.sortDialog.view.SortDialogFragment
import dagger.Subcomponent

@ListingScope
@Subcomponent(modules = [ListModule::class, SortModule::class])
interface ListComponent {
    fun inject(target: MovieListFragment)

    fun inject(fragment: SortDialogFragment)
}