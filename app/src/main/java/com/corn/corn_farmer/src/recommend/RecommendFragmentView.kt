package com.corn.corn_farmer.src.recommend

import com.corn.corn_farmer.src.recommend.model.getRecommendMovieAPI
import com.corn.corn_farmer.src.detail.model.putMovieLike

interface RecommendFragmentView {
    fun onGetRecommendSuccess(response: getRecommendMovieAPI)
    fun onGetRecommendFailure(message: String)
    fun onPutMovieLikeSuccess(response: putMovieLike)
    fun onPutMovieLikeFailure(message: String)
}