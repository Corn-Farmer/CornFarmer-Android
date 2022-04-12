package com.corn.corn_farmer.src.my_comment.model

data class getMyComment(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : ArrayList<getMyCommentResult>
)

data class getMyCommentResult(
    var nickname : String,
    var reviewIdx : Int,
    var movie : getMovie,
    var content : String,
    var rate : Float,
    var createdAt : String,
    var likeCnt : Int
)

data class getMovie(
    var movieIdx : Int,
    var movieTitle : String,
    var moviePhoto: String
)
