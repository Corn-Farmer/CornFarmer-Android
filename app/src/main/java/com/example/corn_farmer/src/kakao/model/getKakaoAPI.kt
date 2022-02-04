package com.example.corn_farmer.src.kakao.model

import com.example.corn_farmer.src.comment.model.getReviewResult

data class getKakaoAPI(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: getKakaoResult?
)

data class getKakaoResult(
    var isNew: Boolean,
    var token: String,
    var userIdx: Int
)