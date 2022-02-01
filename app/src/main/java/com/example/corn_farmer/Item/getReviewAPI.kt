package com.example.corn_farmer.Item

data class getReviewAPI(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : getReviewResult?
)

data class getReviewResult(var reviewIdx : Int)
