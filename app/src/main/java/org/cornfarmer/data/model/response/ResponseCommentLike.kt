package org.cornfarmer.data.model.response

data class ResponseCommentLike(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: CommentLikeResult
)

data class CommentLikeResult(
    var msg: String
)
