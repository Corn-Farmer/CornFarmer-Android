package com.example.corn_farmer.src.kakao.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoRetrofitInterface {

    @GET("/users/outh/kakao")
    fun getUserInfo(@Query("accessToken") accessToken: String): Call<KakaoResponse>
}