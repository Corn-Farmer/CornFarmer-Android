package com.example.corn_farmer.Item

data class getMovieDetailAPI(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : getMovieDetailResult?
)

data class getMovieDetailResult(
    var movieName : String,
    var releaseYear : Int,
    var moviePhotoList : ArrayList<String>,
    var movieGenreList : ArrayList<String>,
    var isLiked : Boolean,
    var likeCnt : Int,
    var ottList : ArrayList<getOttList>,
    var synopsis : String,
    var reviewList : ArrayList<getReviewList>
)

data class getOttList(
    var ottIdx : Int,
    var ottName : String,
    var ottImage : String
)

data class getReviewList(
    var reviewIdx : Int,
    var writerIdx : Int,
    var writer : getReviewWriterInfo,
    var contents : String,
    var rate : Float,
    var likeCnt : Int,
    var createdAt : String
)

data class getReviewWriterInfo(
    var writerIdx : Int,
    var writerNickname : String,
    var writerPhoto : String
)
