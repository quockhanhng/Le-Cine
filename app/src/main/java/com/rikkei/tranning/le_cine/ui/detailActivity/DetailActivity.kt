package com.rikkei.tranning.le_cine.ui.detailActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailFragment
import com.rikkei.tranning.le_cine.util.MOVIE

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (savedInstanceState == null) {
            val extras = intent.extras
            if ((extras != null) && (extras.containsKey(MOVIE))) {
                val movie = extras.getParcelable(MOVIE) as Movie
                val movieDetailFragment = MovieDetailFragment.getInstance(movie)
                supportFragmentManager.beginTransaction().add(R.id.movie_detail_container, movieDetailFragment).commit()
            }
        }
    }
}
