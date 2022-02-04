package com.example.corn_farmer.src.kakao.model

import com.example.corn_farmer.src.comment.model.getReviewAPI
import com.example.corn_farmer.src.comment.model.sendReviewAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoRetrofitInterface {
    @POST("/users/oauth/kakao")
    fun sendToken(@Body sendKakaoAPI: sendKakaoAPI) : Call<getKakaoAPI>
}