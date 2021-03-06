package com.corn.corn_farmer.src.recommend.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RecommendRetrofitInterface {
    @GET("/movies/keywords/{keywordIdx}")
    fun getRecommendInfo(@Path("keywordIdx") keywordIdx:Int, @Header("X-ACCESS-TOKEN") token : String) : Call<getRecommendMovieAPI>
}