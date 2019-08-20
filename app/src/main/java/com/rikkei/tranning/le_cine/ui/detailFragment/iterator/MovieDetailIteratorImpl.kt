package com.rikkei.tranning.le_cine.ui.detailFragment.iterator

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.model.*
import io.reactivex.Observable

class MovieDetailIteratorImpl(private var tmdbWebService: TmdbWebService) : MovieDetailIterator {

    override fun getTrailers(id: String): Observable<List<Video>> {
        return tmdbWebService.trailers(id).map(VideoResponse::getVideosList)
    }

    override fun getReviews(id: String): Observable<List<Review>> {
        return tmdbWebService.reviews(id).map(ReviewResponse::getReviewsList)
    }

    override fun getCasts(id: String): Observable<List<Cast>> {
        return tmdbWebService.casts(id).map(CastResponse::getCastsList)
    }
}