package com.rikkei.tranning.le_cine.ui.listFragment.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.rikkei.tranning.le_cine.App
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Genre
import com.rikkei.tranning.le_cine.ui.listFragment.adapter.MovieListAdapter
import com.rikkei.tranning.le_cine.ui.listFragment.adapter.MovieListAdapter.Companion.SPAN_COUNT_ONE
import com.rikkei.tranning.le_cine.ui.listFragment.adapter.MovieListAdapter.Companion.SPAN_COUNT_THREE
import com.rikkei.tranning.le_cine.ui.listFragment.presenter.MoviesListPresenter
import com.rikkei.tranning.le_cine.ui.sortDialog.view.SortDialogFragment
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : Fragment(), MoviesListView {

    @Inject
    lateinit var presenter: MoviesListPresenter

    private var callback: Callback? = null
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var genresList: List<Genre>

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        callback = context as Callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        setHasOptionsMenu(true)
        (activity!!.application as App).createListComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        presenter.getGenres()
        presenter.setView(this)
    }

    override fun getGenres(genres: List<Genre>) {
        this.genresList = genres
    }

    private fun initLayout() {
        gridLayoutManager = GridLayoutManager(context, SPAN_COUNT_THREE)
        movies_list.setHasFixedSize(true)
        movies_list.layoutManager = gridLayoutManager
        movieListAdapter = MovieListAdapter(this, gridLayoutManager)
        movies_list.adapter = movieListAdapter

        movies_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1))
                    presenter.nextPage()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_item_sort -> {
//                presenter.firstPage()
                displaySortingOptions()
                true
            }
            R.id.menu_switch_layout -> {
                switchItemIcon(item)
                switchLayout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun switchLayout() {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager.spanCount = SPAN_COUNT_THREE
        } else {
            gridLayoutManager.spanCount = SPAN_COUNT_ONE
        }
        movieListAdapter.notifyItemRangeChanged(0, movieListAdapter.itemCount)
    }

    private fun switchItemIcon(item: MenuItem) {
        if (gridLayoutManager.spanCount == SPAN_COUNT_THREE) {
            item.setIcon(R.drawable.ic_span_3)
        } else {
            item.setIcon(R.drawable.ic_span_1)
        }
    }

    private fun displaySortingOptions() {
        val sortDialogFragment = SortDialogFragment.newInstance(presenter)
        sortDialogFragment.show(fragmentManager, "Select Quantity")
    }

    override fun showMovies(movies: ArrayList<Movie>?) {
        movieListAdapter.listGenres = genresList
        movieListAdapter.addMovies(movies)
    }

    override fun clearMovies() {
        movieListAdapter.clear()
    }

    override fun loadingStarted() {
        startProgressBar()
    }

    override fun loadingFailed(errorMessage: String) {
        Snackbar.make(movies_list, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun startProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun stopProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onMovieClicked(movie: Movie) {
        movie.genresString = movie.getGenres(genresList)
        callback?.onMovieClicked(movie)
    }

    fun searchViewClicked(searchText: String) {
        presenter.searchMovie(searchText)
    }

    fun searchViewBackButtonClicked() {
        presenter.searchMovieBackPressed()
    }


    override fun onDetach() {
        super.onDetach()

        callback = null
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.destroy()
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity!!.application as App).releaseListComponent()
    }

    interface Callback {
        fun onMovieClicked(movie: Movie)
    }
}