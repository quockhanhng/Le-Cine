package com.rikkei.tranning.le_cine.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.detailActivity.DetailActivity
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailFragment
import com.rikkei.tranning.le_cine.ui.listFragment.view.MovieListFragment
import com.rikkei.tranning.le_cine.util.MOVIE
import com.rikkei.tranning.le_cine.util.unsubscribe
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MovieListFragment.Callback {

    private var isInitDetailActivity: Boolean = false
    private var searchViewTextSubscription: Disposable? = null
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.menu_item_search)
        val searchView = searchItem?.actionView as SearchView
        val mlFragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as MovieListFragment?

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                val fragment = supportFragmentManager.findFragmentById(R.id.list_fragment) as MovieListFragment?
                fragment?.searchViewBackButtonClicked()
                return true
            }
        })

        searchViewTextSubscription = RxSearchView.queryTextChanges(searchView)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe { charSequence ->
                if (charSequence.isNotEmpty()) {
                    mlFragment?.searchViewClicked(charSequence.toString())
                }
            }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)

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

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else
            Toast.makeText(this, "Tap BACK button again in order to exit", Toast.LENGTH_SHORT).show()
        mBackPressed = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()

        unsubscribe(searchViewTextSubscription)
    }
}
