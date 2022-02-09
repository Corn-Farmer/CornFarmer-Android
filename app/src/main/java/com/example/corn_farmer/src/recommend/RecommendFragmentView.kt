package com.example.corn_farmer.src.recommend

import com.example.corn_farmer.src.recommend.model.getRecommendMovieAPI
import com.example.corn_farmer.src.recommend.model.putMovieLike

interface RecommendFragmentView {
    fun onGetRecommendSuccess(response: getRecommendMovieAPI)
    fun onGetRecommendFailure(message: String)
    fun onPutMovieLikeSuccess(response: putMovieLike)
    fun onPutMovieLikeFailure(message: String)
}