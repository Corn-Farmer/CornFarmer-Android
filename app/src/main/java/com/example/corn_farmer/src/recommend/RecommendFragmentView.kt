package com.example.corn_farmer.src.recommend

import com.example.corn_farmer.src.recommend.model.getRecommendMovieAPI

interface RecommendFragmentView {
    fun onGetRecommendSuccess(response: getRecommendMovieAPI)
    fun onGetRecommendFailure(message: String)
}