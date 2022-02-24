package com.corn.corn_farmer.src.join.model

data class sendJoinAPI(
    var userNickname: String,
    var phtoto: String,
    var is_male: Boolean,
    var birth: String,
    var ottList: List<String>,
    var genreList: List<String>
)