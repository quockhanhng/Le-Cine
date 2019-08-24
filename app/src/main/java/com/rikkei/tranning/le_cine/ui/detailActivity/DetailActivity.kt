package com.rikkei.tranning.le_cine.ui.detailActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.detailFragment.view.MovieDetailFragment
import com.rikkei.tranning.le_cine.ui.personActivity.PersonDetailActivity
import com.rikkei.tranning.le_cine.ui.personFragment.view.PersonDetailFragment
import com.rikkei.tranning.le_cine.util.MOVIE
import com.rikkei.tranning.le_cine.util.PERSON_ID

class DetailActivity : AppCompatActivity(), MovieDetailFragment.Callback {

    private var isInitPersonDetailActivity: Boolean = false

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

        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        if (findViewById<FrameLayout>(R.id.person_detail_container) != null) {
            isInitPersonDetailActivity = true
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.person_detail_container, PersonDetailFragment())
                    .commit()
            }
        } else isInitPersonDetailActivity = false

    }

    override fun onPersonClicked(id: String) {
        if (isInitPersonDetailActivity)
            loadMovieFragment(id)
        else
            startMovieActivity(id)
    }

    private fun loadMovieFragment(id: String) {
        val personDetailFragment = PersonDetailFragment.getInstance(id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.person_detail_container, personDetailFragment, "PersonDetailFragment")
            .commit()
    }

    private fun startMovieActivity(id: String) {
        val intent = Intent(this, PersonDetailActivity::class.java)
        val extras = Bundle()
        extras.putString(PERSON_ID, id)
        intent.putExtras(extras)
        startActivity(intent)
    }
}
