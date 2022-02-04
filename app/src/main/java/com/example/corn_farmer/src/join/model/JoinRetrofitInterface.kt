package com.example.corn_farmer.src.join.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface JoinRetrofitInterface {
    @POST("/users")
    fun sendJoin(@Body sendJoinAPI: sendJoinAPI): Call<getJoinAPI>
}