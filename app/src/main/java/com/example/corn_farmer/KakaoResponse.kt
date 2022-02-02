package com.example

import com.google.gson.annotations.SerializedName

data class Kresult(@SerializedName("isNew") val isNew: Boolean,
                   @SerializedName("token") val token: String,
                   @SerializedName("userIdx") val userIdx: Int)

data class KakaoResponse(
    @SerializedName("isSuccess") val isSuccess : Boolean,
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Kresult?
)