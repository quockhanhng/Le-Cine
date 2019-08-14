package com.rikkei.tranning.le_cine.ui.favourite

import com.rikkei.tranning.le_cine.model.Movie

class FavouriteIteratorImpl(private var databaseHelper: DatabaseHelper) : FavouriteIterator {

    override fun setFavorite(movie: Movie) {
        databaseHelper.setFavourite(movie)
    }

    override fun isFavorite(id: String): Boolean {
        return databaseHelper.isFavourite(id)
    }

    override fun getFavorites(): List<Movie> {
        return databaseHelper.getFavourites()
    }

    override fun unFavorite(id: String) {
        databaseHelper.unFavourite(id)
    }
}