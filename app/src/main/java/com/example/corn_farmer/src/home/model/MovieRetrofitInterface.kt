package com.example.corn_farmer.src.home.model

import com.example.corn_farmer.src.home.model.MovieResponse
import com.example.corn_farmer.src.keyword.model.KeywordResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieRetrofitInterface {
    @GET("/movies/today")
    fun getMovieList(): Call<MovieResponse>


}