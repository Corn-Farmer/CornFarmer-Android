package com.corn.corn_farmer.src.detail.model

data class getDeclationUserAPI(
    var isSuccess : Boolean,
    var code : Int,
    var message : String,
    var result : getDeclationResult,
    var reportUser: Boolean,
    var banUser: Boolean
)

data class getDeclationResult(
    var reportIdx :  Int
)
