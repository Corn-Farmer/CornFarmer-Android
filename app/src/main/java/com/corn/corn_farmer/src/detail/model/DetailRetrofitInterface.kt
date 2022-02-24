package com.corn.corn_farmer.src.detail.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailRetrofitInterface {
        @GET("/movies/{movieIdx}")
        fun getMovieInfo(@Path("movieIdx") movieIdx:Int, @Query("sort") sort:String,@Header("X-ACCESS-TOKEN")token : String) : Call<getMovieDetailAPI>
}