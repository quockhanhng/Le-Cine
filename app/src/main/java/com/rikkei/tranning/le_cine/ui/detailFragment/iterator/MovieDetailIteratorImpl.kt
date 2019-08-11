package com.rikkei.tranning.le_cine.ui.detailFragment.iterator

import com.rikkei.tranning.le_cine.api.TmdbWebService
import com.rikkei.tranning.le_cine.model.Review
import com.rikkei.tranning.le_cine.model.ReviewResponse
import com.rikkei.tranning.le_cine.model.Video
import com.rikkei.tranning.le_cine.model.VideoResponse
import io.reactivex.Observable

class MovieDetailIteratorImpl(private var tmdbWebService: TmdbWebService) : MovieDetailIterator {

    override fun getTrailers(id: String): Observable<List<Video>> {
        return tmdbWebService.trailers(id).map(VideoResponse::getVideosList)
    }

    override fun getReviews(id: String): Observable<List<Review>> {
        return tmdbWebService.reviews(id).map(ReviewResponse::getReviewsList)
    }
}