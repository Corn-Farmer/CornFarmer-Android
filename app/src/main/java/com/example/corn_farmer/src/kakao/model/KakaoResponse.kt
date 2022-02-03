package com.example.corn_farmer.src.kakao.model

import com.google.gson.annotations.SerializedName

data class KakaoResult(@SerializedName("isNew") val isNew: Boolean,
                       @SerializedName("token") val token: String,
                       @SerializedName("userIdx") val userIdx: Int)

data class KakaoResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: KakaoResult?
)