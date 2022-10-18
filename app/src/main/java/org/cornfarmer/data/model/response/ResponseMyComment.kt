package org.cornfarmer.data.model.response

data class ResponseMyComment(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<MyCommentResult>
)

data class MyCommentResult(
    var nickname: String,
    var reviewIdx: Int,
    var movie: GetMovie,
    var content: String,
    var rate: Float,
    var createdAt: String,
    var likeCnt: Int
)

data class GetMovie(
    var movieIdx: Int,
    var movieTitle: String,
    var moviePhoto: String
)
