package org.cornfarmer.data.model.response

data class ResponseMovieDetail(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: MovieDetailResult?
)

data class MovieDetailResult(
    var movieName: String,
    var releaseYear: Int,
    var moviePhotoList: ArrayList<String>,
    var movieGenreList: ArrayList<String>,
    var likeCnt: Int,
    var ottList: ArrayList<OttList>?,
    var synopsis: String,
    var reviewList: ArrayList<ReviewList>,
    var liked: Boolean,
    var director: String,
    var actorList: ArrayList<String>
)

data class OttList(
    var ottIdx: Int?,
    var ottName: String?,
    var ottImage: String?
)

data class ReviewList(
    var reviewIdx: Int,
    var writerIdx: Int,
    var writer: ReviewWriterInfo,
    var contents: String,
    var rate: Float,
    var likeCnt: Int,
    var createAt: String,
    var liked: Boolean

)

data class ReviewWriterInfo(
    var writerIdx: Int,
    var writerNickname: String,
    var writerPhoto: String
)
