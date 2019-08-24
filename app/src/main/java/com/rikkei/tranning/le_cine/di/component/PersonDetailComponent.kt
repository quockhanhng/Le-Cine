package com.rikkei.tranning.le_cine.di.component

import com.rikkei.tranning.le_cine.di.module.PersonDetailModule
import com.rikkei.tranning.le_cine.di.scope.PersonDetailScope
import com.rikkei.tranning.le_cine.ui.personFragment.view.PersonDetailFragment
import dagger.Subcomponent

@PersonDetailScope
@Subcomponent(modules = [PersonDetailModule::class])
interface PersonDetailComponent {
    fun inject(target: PersonDetailFragment)
}