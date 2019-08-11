package com.rikkei.tranning.le_cine.ui.listFragment.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rikkei.tranning.le_cine.R
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.ui.listFragment.view.MoviesListView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_layout.view.*
import java.util.ArrayList

class MovieListAdapter(private var moviesView: MoviesListView) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var movies: List<Movie> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener(holder)
        holder.movie = movies[position]
        holder.bind()
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = (LayoutInflater.from(parent.context).inflate(R.layout.item_movie_layout, parent, false))
        return ViewHolder(root)
    }

    fun addMovies(movies: List<Movie>?) {
        if (movies != null) {
            this.movies = movies
            notifyDataSetChanged()
        }
        Log.d("Log MovieListAdapter", this.movies.size.toString())
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        lateinit var movie: Movie

        override fun onClick(v: View?) {
            this@MovieListAdapter.moviesView.onMovieClicked(movie)
        }

        fun bind() = with(itemView) {
            title.text = movie.title

            Picasso.get()
                .load(movie.getPosterUrl())
                .into(poster)
        }
    }
}