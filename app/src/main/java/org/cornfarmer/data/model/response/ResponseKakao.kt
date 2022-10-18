package org.cornfarmer.data.model.response

data class ResponseKakao(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: KakaoResult?
)

data class KakaoResult(
    var new_result: Boolean,
    var token: String,
    var userIdx: Int
)
