package com.example.corn_farmer.src.keyword.model

import com.google.gson.annotations.SerializedName

data class KeywordResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean?,
    @SerializedName("code") var code : Int?,
    @SerializedName("message") var message : String?,
    @SerializedName("result") var result : List<KeywordDto>
)

data class KeywordDto(
    @SerializedName("keywordIdx") var keywordIdx : Int?,
    @SerializedName("keyword") var keyword : String?
)
