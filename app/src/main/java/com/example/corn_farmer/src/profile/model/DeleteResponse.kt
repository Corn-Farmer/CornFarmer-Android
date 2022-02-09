package com.example.corn_farmer.src.profile.model

data class DeleteResponse(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : DeleteResult

)

data class DeleteResult(
    var userIdx : Int
)
