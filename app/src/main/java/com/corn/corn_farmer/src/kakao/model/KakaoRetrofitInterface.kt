package com.corn.corn_farmer.src.kakao.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoRetrofitInterface {
    @POST("/users/oauth/kakao")
    fun sendToken(@Body sendKakaoAPI: sendKakaoAPI) : Call<getKakaoAPI>
}