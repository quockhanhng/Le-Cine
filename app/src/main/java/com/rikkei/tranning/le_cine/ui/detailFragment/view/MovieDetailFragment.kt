package com.rikkei.tranning.le_cine.ui.detailFragment.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.VisibilityAwareImageButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
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

class MovieDetailFragment : Fragment(), MovieDetailView, View.OnClickListener, MenuItem.OnMenuItemClickListener {

    @Inject
    lateinit var presenter: MovieDetailPresenter

    private lateinit var movie: Movie
    private var appBarExpanded = true
    private var collapsedMenu: Menu? = null

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
        setHasOptionsMenu(true)
        (activity?.application as App).createDetailComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBar()
        setAppBar()

        if (arguments != null) {
            val movie = arguments!!.get(MOVIE) as Movie

            this.movie = movie
            presenter.setView(this)
            presenter.showDetails(movie)
            presenter.showFavouriteButton(movie)
        }

        fabFavourite.setOnClickListener(this)

        collapsing_toolbar?.title = movie.title
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

    @SuppressLint("RestrictedApi")
    private fun setAppBar() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->

            if (Math.abs(verticalOffset) > 280) {
                appBarExpanded = false
                fabFavourite.visibility = VisibilityAwareImageButton.GONE
                activity?.invalidateOptionsMenu()

            } else {
                appBarExpanded = true
                fabFavourite.visibility = VisibilityAwareImageButton.VISIBLE
                presenter.showFavouriteButton(movie)
                activity?.invalidateOptionsMenu()
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        if ((collapsedMenu != null) && (!appBarExpanded)) {
            //collapsed

            if (presenter.isFavouriteMovie(movie)) {
                collapsedMenu!!.add("AddToFavourite")
                    .setIcon(R.drawable.ic_full_heart)
                    .setOnMenuItemClickListener(this)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            } else {
                collapsedMenu!!.add("AddToFavourite")
                    .setIcon(R.drawable.ic_border_heart)
                    .setOnMenuItemClickListener(this)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            }
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.detail_menu, menu)
        collapsedMenu = menu
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        presenter.onFavouriteClick(movie)
        return true
    }

    @SuppressLint("SetTextI18n")
    override fun showDetails(movie: Movie) {
        if (movie.backdropPath != null) {
            Picasso.get()
                .load(movie.getBackDropPath())
                .into(movie_poster)
        } else {
            movie_poster.setImageResource(R.drawable.default_poster)
        }
        movie_name.text = movie.title
        movie_genres.text = "Genre: " + movie.genresString
        movie_year.text = "Release Date: ${movie.releaseDate}"
        movie_rating.text = if (movie.voteAverage != 0.0) "${movie.voteAverage} / 10" else "--"
        movie_description.text = movie.overview
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
                val titleView = thumbContainer.findViewById<TextView>(R.id.video_title)
                thumbView.setTag(R.id.trailer_tag, Video.getUrl(trailer))
                thumbView.requestLayout()
                thumbView.setOnClickListener(this)
                Picasso.get()
                    .load(Video.getThumbnailUrl(trailer))
                    .resize(160, 130)
                    .centerCrop()
                    .into(thumbView)
                titleView.text = trailer.name
                this.trailers.addView(thumbContainer)
            }
        }
    }

    override fun showReviews(reviews: List<Review>) {
        if (reviews.isEmpty()) {
            reviews_label.visibility = View.GONE
            this.reviews_container.visibility = View.GONE
            reviews_container.visibility = View.GONE
        } else {
            reviews_label.visibility = View.VISIBLE
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

    override fun showFavourite() {
        fabFavourite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_full_heart))
    }

    override fun showUnFavourite() {
        fabFavourite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_border_heart))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.video_thumb -> {
                onThumbnailClicked(v)
            }
            R.id.review_content -> {
                onReviewClicked(v as TextView)
            }

            R.id.fabFavourite -> {
                onFavouriteClicked()
            }
        }
    }

    private fun onThumbnailClicked(v: View) {
        val videoUrl = v.getTag(R.id.trailer_tag) as String
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        startActivity(intent)
    }

    private fun onReviewClicked(view: TextView) {
        if (view.maxLines == 5)
            view.maxLines = 500
        else view.maxLines = 5
    }

    private fun onFavouriteClicked() {
        presenter.onFavouriteClick(movie)
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity?.application as App).releaseDetailComponent()
    }
}