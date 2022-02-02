package com.example.corn_farmer.Retrofit

import com.example.corn_farmer.src.home.model.MovieResponse
import com.example.corn_farmer.src.keyword.model.KeywordResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitMovieAPI { //->이름 변경
    @GET("/movies/today")
    fun getMovieList(): Call<MovieResponse>

    @GET("movies/keywords")
    fun getKeywordList():Call<KeywordResponse>
}