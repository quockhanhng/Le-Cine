package com.rikkei.tranning.le_cine.ui.listFragment.iterator

import android.annotation.SuppressLint
import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.model.Genre
import com.rikkei.tranning.le_cine.model.GenreResponse
import com.rikkei.tranning.le_cine.model.Movie
import com.rikkei.tranning.le_cine.model.MovieResponse
import com.rikkei.tranning.le_cine.ui.favourite.FavouriteIterator
import com.rikkei.tranning.le_cine.ui.sortDialog.SelectOptionStore
import com.rikkei.tranning.le_cine.ui.sortDialog.SortType
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class MoviesListIteratorImpl(
    private var tmdbWebService: TmdbWebService,
    private var selectOptionStore: SelectOptionStore,
    private var favouriteIterator: FavouriteIterator
) :
    MoviesListIterator {

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val NEWEST_MIN_VOTE_COUNT = 50

    override fun fetchMovies(page: Int): Observable<List<Movie>> {
        return when (selectOptionStore.getSelectedOption()) {
            SortType.MOST_POPULAR.value ->
                tmdbWebService.popularMovies(page).map(MovieResponse::getMovieList)
            SortType.HIGHEST_RATED.value ->
                tmdbWebService.highestRatedMovies(page).map(MovieResponse::getMovieList)
            SortType.NEWEST.value -> {
                val cal = Calendar.getInstance()
                val maxReleaseDate = dateFormat.format(cal.time)
                tmdbWebService.newestMovies(maxReleaseDate, NEWEST_MIN_VOTE_COUNT)
                    .map(MovieResponse::getMovieList)
            }
            else -> {
                return Observable.just(favouriteIterator.getFavorites())
            }
        }
    }

    override fun searchMovie(searchQuery: String): Observable<List<Movie>> {
        return tmdbWebService.searchMovies(searchQuery).map(MovieResponse::getMovieList)
    }

    override fun isMoviesLoadFromFavourites(): Boolean {
        val selectedOption = selectOptionStore.getSelectedOption()
        return selectedOption != SortType.FAVOURITE.value
    }

    override fun getGenres(): Observable<List<Genre>> {
        return tmdbWebService.genres().map(GenreResponse::getGenresList)
    }
}