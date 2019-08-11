package com.rikkei.tranning.le_cine.ui.detailFragment.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rikkei.tranning.le_cine.App
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.Review
import com.rikkei.tranning.le_cine.model.Video
import com.rikkei.tranning.le_cine.ui.detailFragment.presenter.MovieDetailPresenter
import com.rikkei.tranning.le_cine.util.MOVIE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.trailers_and_reviews.*
import javax.inject.Inject

class MovieDetailFragment : Fragment(), MovieDetailView, View.OnClickListener {

    @Inject
    lateinit var presenter: MovieDetailPresenter

    private lateinit var movie: Movie

    companion object {
        fun getInstance(movie: Movie): MovieDetailFragment {
            val args = Bundle()
            args.putParcelable(MOVIE, movie)
            val movieDetailsFragment = MovieDetailFragment()
            movieDetailsFragment.arguments = args
            return movieDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        (activity?.application as App).createDetailComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    private fun setToolBar() {
        collapsing_toolbar?.setContentScrimColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        collapsing_toolbar?.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar)
        collapsing_toolbar?.setExpandedTitleTextAppearance(R.style.ExpandedToolbar)
        collapsing_toolbar?.isTitleEnabled = true

        if (toolbar != null) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()

        if (arguments != null) {
            val movie = arguments!!.get(MOVIE) as Movie

            this.movie = movie
            presenter.setView(this)
            presenter.showDetails(movie)
        }

        collapsing_toolbar?.title = movie.title
    }

    @SuppressLint("SetTextI18n")
    override fun showDetails(movie: Movie) {
        Picasso.get()
            .load(movie.getBackDropPath())
            .into(movie_poster)
        movie_name.text = movie.title
        movie_year.text = "Release Date: ${movie.releaseDate}"
        movie_rating.text = "${movie.voteAverage} / 10"
        trailers_label.text = movie.overview
        presenter.showTrailers(movie)
        presenter.showReviews(movie)
    }

    override fun showTrailers(trailers: List<Video>) {
        if (trailers.isEmpty()) {
            trailers_label.visibility = View.GONE
            this.trailers.visibility = View.GONE
            trailers_container.visibility = View.GONE

        } else {
            trailers_label.visibility = View.VISIBLE
            this.trailers.visibility = View.VISIBLE
            trailers_container.visibility = View.VISIBLE

            this.trailers.removeAllViews()
            val inflater = activity!!.layoutInflater

            for (trailer in trailers) {
                val thumbContainer = inflater.inflate(R.layout.video, this.trailers, false)
                val thumbView = thumbContainer.findViewById<ImageView>(R.id.video_thumb)
                thumbView.setTag(R.id.trailer_tag, Video.getUrl(trailer))
                thumbView.requestLayout()
                thumbView.setOnClickListener(this)
                Picasso.get()
                    .load(Video.getThumbnailUrl(trailer))
                    .resize(150, 150)
                    .centerCrop()
                    .into(thumbView)
                this.trailers.addView(thumbContainer)
            }
        }
    }

    override fun showReviews(reviews: List<Review>) {
        if (reviews.isEmpty()) {
            this.reviews_container.visibility = View.GONE
            reviews_container.visibility = View.GONE
        } else {
            this.reviews_container.visibility = View.VISIBLE
            reviews_container.visibility = View.VISIBLE

            reviews_container.removeAllViews()
            val inflater = activity!!.layoutInflater
            for (review in reviews) {
                val reviewContainer = inflater.inflate(R.layout.review, reviews_container, false) as ViewGroup
                val reviewAuthor = reviewContainer.findViewById<TextView>(R.id.review_author)
                val reviewContent = reviewContainer.findViewById<TextView>(R.id.review_content)
                reviewAuthor.text = review.author
                reviewContent.text = review.content
                reviewContent.setOnClickListener(this)
                reviews_container.addView(reviewContainer)
            }
        }
    }

    override fun onClick(v: View?) {

    }

    override fun onDestroy() {
        super.onDestroy()

        (activity?.application as App).releaseDetailComponent()
    }
}