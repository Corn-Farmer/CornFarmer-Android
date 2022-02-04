package com.example.corn_farmer.src.join.model

data class getJoinAPI(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: getJoinResult
)

data class getJoinResult(
    var userIdx: Int
)