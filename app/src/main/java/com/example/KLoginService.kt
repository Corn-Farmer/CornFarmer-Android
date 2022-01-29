package com.example

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface KLoginService {
    @GET("/oauth/kakao")
    fun klogin(@Query("accessToken") accessToken: String): Call<KakaoResponse>
}