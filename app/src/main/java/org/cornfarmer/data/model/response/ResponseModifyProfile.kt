package org.cornfarmer.data.model.response

data class ModifyProfileResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ModifyProfileResult
)

data class ModifyProfileResult(
    var nickname: String,
    var photo: String,
    var ottList: ArrayList<String>,
    var genreList: ArrayList<String>,
    var is_male: Int,
    var birth: String
)
