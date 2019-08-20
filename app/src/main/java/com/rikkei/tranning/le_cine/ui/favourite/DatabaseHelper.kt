package com.rikkei.tranning.le_cine.ui.favourite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rikkei.tranning.le_cine.model.Movie
import android.database.Cursor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseHelper @Inject constructor(context: Context?) : SQLiteOpenHelper(context, "FavouriteMovies.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE MOVIE (ID TEXT,OVERVIEW TEXT,RELEASE_DATE TEXT,POSTER_PATH TEXT,BACKDROP_PATH TEXT,TITLE TEXT, GENRE_IDS TEXT,VOTE_AVERAGE DOUBLE, GENRES_STRING TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MOVIE")
        onCreate(db)
    }

    fun setFavourite(movie: Movie) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ID", movie.id)
        contentValues.put("OVERVIEW", movie.overview)
        contentValues.put("RELEASE_DATE", movie.releaseDate)
        contentValues.put("POSTER_PATH", movie.posterPath)
        contentValues.put("BACKDROP_PATH", movie.backdropPath)
        contentValues.put("TITLE", movie.title)
        contentValues.put("GENRE_IDS", movie.genreIds.toString())
        contentValues.put("VOTE_AVERAGE", movie.voteAverage)
        contentValues.put("GENRES_STRING", movie.genresString)

        db.insert("MOVIE", null, contentValues)
        db.close()
    }

    fun isFavourite(id: String): Boolean {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM MOVIE WHERE ID = ?"
        val cursor: Cursor? = db.rawQuery(selectQuery, arrayOf(id))
        return if (cursor!!.moveToFirst()) {
            cursor.close()
            true
        } else {
            cursor.close()
            false
        }
    }

    fun getFavourites(): List<Movie> {
        val listMovie = ArrayList<Movie>()
        // Select All Query
        val selectQuery = "SELECT * FROM MOVIE"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                var genres = cursor.getString(6)
                genres = genres.substring(1, genres.length - 1)
                genres = genres.replace(" ", "")
                val item = genres.split(",")

                val movie = Movie.newMovie(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    item,
                    cursor.getDouble(7),
                    cursor.getString(8)
                )
                listMovie.add(movie)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listMovie
    }

    fun unFavourite(id: String) {
        val db = this.writableDatabase
        db.delete("MOVIE", "ID = ?", arrayOf(id))
        db.close()
    }
}