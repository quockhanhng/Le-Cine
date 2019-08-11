package com.rikkei.tranning.le_cine.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.detailActivity.DetailActivity
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailFragment
import com.rikkei.tranning.le_cine.ui.listFragment.view.MovieListFragment
import com.rikkei.tranning.le_cine.util.MOVIE

class MainActivity : AppCompatActivity(), MovieListFragment.Callback {

    private var isInitDetailActivity: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolBar()
        initView(savedInstanceState)
    }

    private fun setToolBar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setTitle(R.string.app_name)
            supportActionBar!!.setDisplayShowTitleEnabled(true)
        }
    }

    private fun initView(savedInstanceState: Bundle?) {
        if (findViewById<FrameLayout>(R.id.movie_detail_container) != null) {
            isInitDetailActivity = true
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.movie_detail_container, MovieDetailFragment())
                    .commit()
            }
        } else isInitDetailActivity = false
    }

    override fun onMovieClicked(movie: Movie) {
        if (isInitDetailActivity)
            loadMovieFragment(movie)
        else
            startMovieActivity(movie)
    }

    private fun loadMovieFragment(movie: Movie) {
        val movieDetailFragment = MovieDetailFragment.getInstance(movie)
        supportFragmentManager.beginTransaction()
            .replace(R.id.movie_detail_container, movieDetailFragment, "DetailFragment")
            .commit()
    }

    private fun startMovieActivity(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        val extras = Bundle()
        extras.putParcelable(MOVIE, movie)
        intent.putExtras(extras)
        startActivity(intent)
    }
}
