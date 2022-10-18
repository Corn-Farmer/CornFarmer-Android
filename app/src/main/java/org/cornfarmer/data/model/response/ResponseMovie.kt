package org.cornfarmer.data.model.response

data class MovieResponse(
    var isSuccess: Boolean?,
    var message: String?,
    var code: Int?,
    var result: List<MovieDto> = arrayListOf()
)

data class MovieDto(
    var movieIdx: Int?,
    var movieName: String?,
    var moviePhotoList: List<String> = arrayListOf(),
    var movieGenreList: List<String> = arrayListOf(),
    var likeCnt: Int?,
    var liked: Boolean?
)
