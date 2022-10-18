package org.cornfarmer.data.model.response

data class ResponseWishMovie(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<WishMovieResult>
)

data class WishMovieResult(
    var movieIdx: Int,
    var movieTitle: String,
    var moviePhoto: String,
    var movieGenre: String,
    var movieLikeCnt: Int
)
