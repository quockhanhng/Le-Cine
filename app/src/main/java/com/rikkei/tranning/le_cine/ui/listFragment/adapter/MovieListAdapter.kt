package com.rikkei.tranning.le_cine.ui.listFragment.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Genre
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.listFragment.view.MoviesListView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_layout_span_1.view.*
import kotlinx.android.synthetic.main.item_movie_layout_span_3.view.*
import java.util.ArrayList

class MovieListAdapter(private var moviesView: MoviesListView, private var layoutManager: GridLayoutManager) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var movies: ArrayList<Movie> = ArrayList()
    var listGenres: List<Genre>? = null

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_THREE = 3

        const val VIEW_TYPE_SMALL = 1
        const val VIEW_TYPE_BIG = 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(holder)
        holder.movie = movies[position]
        holder.bind()
    }

    override fun getItemCount() = movies.size

    override fun getItemViewType(position: Int): Int {
        val spanCount = layoutManager.spanCount
        return if (spanCount == SPAN_COUNT_ONE) {
            VIEW_TYPE_BIG
        } else {
            VIEW_TYPE_SMALL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = if (viewType == VIEW_TYPE_BIG) {
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout_span_1, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout_span_3, parent, false)
        }

        return ViewHolder(view, viewType)
    }

    fun addMovies(movies: ArrayList<Movie>?) {
        if (movies != null) {
            this.movies = movies
            notifyDataSetChanged()
        }
    }

    fun clear() {
        movies.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View, private var viewType: Int) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        lateinit var movie: Movie

        override fun onClick(v: View?) {
            this@MovieListAdapter.moviesView.onMovieClicked(movie)
        }

        fun bind() = with(itemView) {
            if (viewType == VIEW_TYPE_BIG) {
                item_title_span_1.text = movie.title

                if (movie.posterPath != null)
                    Picasso.get()
                        .load(movie.getPosterUrl())
                        .into(item_poster_span_1)
                else
                    item_poster_span_1.setImageResource(R.drawable.default_poster)

                item_year_span_1.text = movie.releaseDate
                item_genres_span_1.text = movie.getGenres(listGenres)
                item_rated_span_1.text = if (movie.voteAverage != 0.0) movie.voteAverage.toString() else "--"
            } else {
                item_title_span_3.text = movie.title

                if (movie.posterPath != null)
                    Picasso.get()
                        .load(movie.getPosterUrl())
                        .into(item_poster_span_3)
                else item_poster_span_3.setImageResource(R.drawable.default_poster)
            }
        }
    }
}