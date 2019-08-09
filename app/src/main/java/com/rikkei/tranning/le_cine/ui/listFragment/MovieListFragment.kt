package com.rikkei.tranning.le_cine.ui.listFragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rikkei.tranning.le_cine.App
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.R
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(), MoviesListView {

    @Inject
    lateinit var presenter: MoviesListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (context?.applicationContext as App).createListComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        presenter.setView(this)
    }

    private fun initLayout() {

    }

    override fun showMovies(movies: List<Movie>?) {

    }

    override fun loadingStarted() {
        Snackbar.make(movies_list, "Loading Movies", Snackbar.LENGTH_SHORT).show()
    }

    override fun loadingFailed(errorMessage: String) {
        Snackbar.make(movies_list, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onMovieClicked(movie: Movie) {
    }
}