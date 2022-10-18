package org.cornfarmer.data.model.request

data class RequestMovieLike(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: MovieLike
)

data class MovieLike(
    var msg: String
)
