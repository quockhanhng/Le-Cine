package com.rikkei.tranning.le_cine.ui.favourite

import com.rikkei.tranning.le_cine.model.Movie

interface FavouriteIterator {
    fun setFavorite(movie: Movie)
    fun isFavorite(id: String): Boolean
    fun getFavorites(): List<Movie>
    fun unFavorite(id: String)
}