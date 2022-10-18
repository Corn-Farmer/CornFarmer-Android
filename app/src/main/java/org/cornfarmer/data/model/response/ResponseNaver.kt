package org.cornfarmer.data.model.response

data class ResponseNaver(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: NaverResult?
)

data class NaverResult(
    var new_result: Boolean,
    var token: String,
    var userIdx: Int
)
