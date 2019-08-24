package com.rikkei.tranning.le_cine.ui.personFragment.presenter

import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.Person
import com.rikkei.tranning.le_cine.ui.personFragment.view.PersonDetailView

interface PersonDetailPresenter {
    fun setView(view: PersonDetailView)

    fun showInfo(id: String)

    fun onMovieClicked(movie: Movie)

    fun destroy()
}