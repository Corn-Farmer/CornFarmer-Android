package com.corn.corn_farmer.src.wishlist.model

data class getWishMovie(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : ArrayList<getWishMovieResult>
)

data class getWishMovieResult(
    var movieIdx : Int,
    var movieTitle : String,
    var moviePhoto : String,
    var movieGenre : String,
    var movieLikeCnt : Int
)
