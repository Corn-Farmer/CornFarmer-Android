package com.corn.corn_farmer.src.keyword.model

import retrofit2.Call
import retrofit2.http.GET

interface KeywordRetrofitInterface {
    @GET("movies/keywords")
    fun getKeywordList(): Call<KeywordResponse>
}