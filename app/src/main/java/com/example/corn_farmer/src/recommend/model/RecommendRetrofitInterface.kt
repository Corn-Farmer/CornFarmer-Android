package com.example.corn_farmer.src.recommend.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendRetrofitInterface {
    @GET("/movies/keywords/{keywordIdx}")
    fun getRecommendInfo(@Path("keywordIdx") keywordIdx:Int, @Path("X-ACCESS-TOKEN") token : String) : Call<getRecommendMovieAPI>
}