package com.rikkei.tranning.le_cine.ui.personFragment.presenter

import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.Person
import com.rikkei.tranning.le_cine.ui.personFragment.iterator.PersonDetailIterator
import com.rikkei.tranning.le_cine.ui.personFragment.view.PersonDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PersonDetailPresenterImpl(private var iterator: PersonDetailIterator) : PersonDetailPresenter {

    private var view: PersonDetailView? = null
    private lateinit var personSubscription: Disposable

    override fun setView(view: PersonDetailView) {
        this.view = view
    }

    override fun showInfo(id: String) {
        personSubscription = iterator.getPersonDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onGetPersonInfoSuccess(it) }, { onGetPersonInfoFailure(it) })
    }

    private fun onGetPersonInfoSuccess(person: Person) {
        view?.showInfo(person)
    }

    private fun onGetPersonInfoFailure(e: Throwable) {
        view?.showError(e.toString())
    }

    override fun onMovieClicked(movie: Movie) {
    }

    override fun destroy() {
        view = null
    }
}