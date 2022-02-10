package com.example.corn_farmer.src.search_result.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchResultRetrofitInterface {
    @GET("/movies/search")
    fun getSearchInfo(@Query("keyword")keyword:String?,@Query("sort") sort : String?,@Header("X-ACCESS-TOKEN")token : String): Call<SearchResultResponse>
}