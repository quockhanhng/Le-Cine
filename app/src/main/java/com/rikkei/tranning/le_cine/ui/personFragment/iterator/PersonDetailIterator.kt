package com.rikkei.tranning.le_cine.ui.personFragment.iterator

import com.rikkei.tranning.le_cine.model.Person
import io.reactivex.Observable

interface PersonDetailIterator {
    fun getPersonDetail(id: String): Observable<Person>
}