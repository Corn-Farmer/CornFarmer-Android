package org.cornfarmer.data.model.response

data class ResponseRecommend(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: RecommendMovieResult
)

data class RecommendMovieResult(
    var keyword: String,
    var movieList: ArrayList<MovieInfo>
)

data class MovieInfo(
    var movieIdx: Int,
    var movieName: String,
    var moviePhotoList: ArrayList<String>,
    var movieGenreList: ArrayList<String>,
    var likeCnt: Int,
    var liked: Boolean
)
