package com.rikkei.tranning.le_cine.ui.personFragment.view

import com.rikkei.tranning.le_cine.model.Person

interface PersonDetailView {
    fun showInfo(person: Person)
    fun showError(message: String)
}