package org.cornfarmer.data.model.response

data class ResponseJoin(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: JoinResult?
)

data class JoinResult(
    var userIdx: Int
)
