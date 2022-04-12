package com.corn.corn_farmer.src.detail.model

data class putMovieLike(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : putMovieLikeResult
)

data class putMovieLikeResult(
    var msg : String
)
