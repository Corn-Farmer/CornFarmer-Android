package com.example.corn_farmer.src.kakao.model

data class getNaverAPI(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: getNaverResult?
)

data class getNaverResult(
    var new_result: Boolean,
    var token: String,
    var userIdx: Int
)