package org.cornfarmer.data.model.response

data class ResponseDelete(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Delete
)

data class Delete(
    var userIdx: Int
)
