package com.example.corn_farmer.src.kakao.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NaverRetrofitInterface {
    @POST("/users/oauth/naver")
    fun sendToken(@Body sendNaverAPI: sendNaverAPI) : Call<getNaverAPI>
}