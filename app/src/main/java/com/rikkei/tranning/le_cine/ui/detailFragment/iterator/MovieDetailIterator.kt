package com.rikkei.tranning.le_cine.ui.detailFragment.iterator

import com.rikkei.tranning.le_cine.model.Video
import com.rikkei.tranning.le_cine.model.Review
import io.reactivex.Observable

interface MovieDetailIterator {

    fun getTrailers(id: String): Observable<List<Video>>

    fun getReviews(id: String): Observable<List<Review>>
}