package com.rikkei.tranning.le_cine.ui.personFragment.iterator

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.model.Person
import io.reactivex.Observable

class PersonDetailIteratorImpl(private var tmdbWebService: TmdbWebService) : PersonDetailIterator {

    override fun getPersonDetail(id: String): Observable<Person> {
        return tmdbWebService.person(id)
    }
}