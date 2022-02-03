package com.example.corn_farmer.src.recommend.model

data class getRecommendMovieAPI(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : getRecommendMovieResult
)

data class getRecommendMovieResult(
    var keyword : String,
    var movieList : ArrayList<movieInfo>
)

data class movieInfo(
    var movieIdx : Int,
    var movieName : String,
    var moviePhotoList : ArrayList<String>,
    var movieGenreList : ArrayList<String>,
    var isLiked : Boolean
)
