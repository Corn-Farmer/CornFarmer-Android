package com.corn.corn_farmer.src.detail.model

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface movieLikeRetrofitInterface {
    @PUT("/movies/{movieIdx}/like")
    fun putMovieLike(@Path("movieIdx") movieIdx : Int, @Header("X-ACCESS-TOKEN") token : String) : Call<putMovieLike>
}