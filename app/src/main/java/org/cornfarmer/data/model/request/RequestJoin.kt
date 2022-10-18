package org.cornfarmer.data.model.request

data class RequestJoin(
    var userNickname: String,
    var phtoto: String,
    var is_male: Boolean,
    var birth: String,
    var ottList: List<String>,
    var genreList: List<String>
)
