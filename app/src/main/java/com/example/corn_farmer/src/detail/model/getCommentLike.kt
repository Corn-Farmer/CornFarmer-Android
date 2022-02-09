package com.example.corn_farmer.src.detail.model

data class getCommentLike(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : getCommentLikeResult
)

data class getCommentLikeResult(
    var msg : String
)
