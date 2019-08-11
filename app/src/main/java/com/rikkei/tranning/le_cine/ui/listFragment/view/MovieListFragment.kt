package com.rikkei.tranning.le_cine.ui.listFragment.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rikkei.tranning.le_cine.App
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.ui.listFragment.adapter.MovieListAdapter
import com.rikkei.tranning.le_cine.ui.listFragment.presenter.MoviesListPresenter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(), MoviesListView {

    @Inject
    lateinit var presenter: MoviesListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity!!.application as App).createListComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        presenter.setView(this)
    }

    private fun initLayout() {
        movies_list.setHasFixedSize(true)
        movies_list.layoutManager = GridLayoutManager(context, 2)
        movies_list.adapter = MovieListAdapter(this)

        movies_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1))
                    presenter.nextPage()
            }
        })
    }

    override fun showMovies(movies: List<Movie>?) {
        (movies_list.adapter as MovieListAdapter).addMovies(movies)
    }

    override fun loadingStarted() {
        Toast.makeText(context, "Loading Movies", Toast.LENGTH_SHORT).show()
    }

    override fun loadingFailed(errorMessage: String) {
        Snackbar.make(movies_list, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onMovieClicked(movie: Movie) {
    }
}