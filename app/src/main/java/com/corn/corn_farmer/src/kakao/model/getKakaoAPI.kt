package com.corn.corn_farmer.src.kakao.model

data class getKakaoAPI(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: getKakaoResult?
)

data class getKakaoResult(
    var new_result: Boolean,
    var token: String,
    var userIdx: Int
)