package com.example.corn_farmer.src.profile.model

data class ProfileResponse(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    val result : ProfileResult
)
data class ProfileResult(
    var nickname : String,
    var photo : String,
    var ottList : ArrayList<ProfileOtt>,
    var genreList : ArrayList<ProfileGenre>,
    var is_male : Int,
    var birth : String
)
data class ProfileOtt(
    var ott_idx : Int,
    var name : String,
    var photo : String

)

data class ProfileGenre(
    var genre_idx : Int,
    var genre_name : String
)
