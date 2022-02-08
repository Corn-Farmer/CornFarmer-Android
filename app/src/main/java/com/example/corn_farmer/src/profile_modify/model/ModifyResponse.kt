package com.example.corn_farmer.src.profile.model

data class ModifyResponse(
    var isSuccess : Boolean?,
    var code : Int?,
    var message : String?,
    val result : ModifyResult?
)
data class ModifyResult(
    var nickname : String,
    var photo : String,
    var ottList : ArrayList<ModifyOtt>,
    var genreList : ArrayList<ModifyGenre>
)
data class ModifyOtt(
    var ott_idx : Int,
    var name : String,
    var photo : String
)

data class ModifyGenre(
    var genre_idx : Int,
    var genre_name : String
)