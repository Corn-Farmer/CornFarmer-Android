package com.example.corn_farmer

import com.example.corn_farmer.Item.getMovieDetailAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface detailMovieService {
    @GET("/movies/{movieIdx}")
    fun getMovieInfo(@Path("movieIdx") movieIdx:Int, @Query("sort") sort:Int) : Call<getMovieDetailAPI>
}