package com.example.corn_farmer.src.join.model

data class sendJoinAPI(
    var userNickname: String,
    var phtoto: String,
    var is_male: Boolean,
    var birth: String,
    var ottList: List<Int>,
    var genreList: List<Int>
)