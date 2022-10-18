package org.cornfarmer.data.model.response

data class ModifyResponse(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    val result: ModifyResult?
)

data class ModifyResult(
    var new_result: Boolean,
    var token: String,
    var userIdx: Int
)
