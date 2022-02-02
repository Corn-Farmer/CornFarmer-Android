package com.example

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoRetrofitInterface {

    @GET("/users/outh/kakao")
    fun kLogin(@Query("accessToken") accessToken: String): Call<KakaoResponse>
}