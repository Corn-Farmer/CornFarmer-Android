package org.cornfarmer.data.model.response

data class ResponseProfile(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    val result: ProfileResult
)

data class ProfileResult(
    var nickname: String,
    var photo: String,
    var ottList: ArrayList<ProfileOtt>,
    var genreList: ArrayList<ProfileGenre>,
    var is_male: Int,
    var birth: String
)

data class ProfileOtt(
    var ott_idx: Int,
    var name: String,
    var photo: String

)

data class ProfileGenre(
    var genre_idx: Int,
    var genre_name: String
)
