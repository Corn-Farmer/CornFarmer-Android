package org.cornfarmer.data.model.response

data class ResponseReview(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ReviewResult?
)

data class ReviewResult(var reviewIdx: Int)
