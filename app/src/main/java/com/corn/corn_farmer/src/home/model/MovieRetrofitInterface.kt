package com.corn.corn_farmer.src.home.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieRetrofitInterface {
    @GET("/movies/today")
    fun getMovieList(@Header("X-ACCESS-TOKEN") token : String): Call<MovieResponse>


}