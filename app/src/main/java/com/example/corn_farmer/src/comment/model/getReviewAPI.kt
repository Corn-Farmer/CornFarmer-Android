package com.example.corn_farmer.src.comment.model

data class getReviewAPI(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : getReviewResult?
)

data class getReviewResult(var reviewIdx : Int)
