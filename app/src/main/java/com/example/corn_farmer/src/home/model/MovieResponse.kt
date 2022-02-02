package com.example.corn_farmer.src.home.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("isSuccess")
    var isSuccess : Boolean?,
    @SerializedName("message")
    var message : String?,
    @SerializedName("code")
    var code : Int?,
    @SerializedName("result")
    var result : List<MovieDto> = arrayListOf()
)

data class MovieDto(
    @SerializedName("movieIdx") var movieIdx : Int?,
    @SerializedName("movieName") var movieName : String?,
    @SerializedName("moviePhotoList") var moviePhotoList : List<String> = arrayListOf(),
    @SerializedName("movieGenreList")var movieGenreList : List<String> = arrayListOf(),
    @SerializedName("liked")var liked : Boolean?,
    @SerializedName("likeCnt")var likeCnt : Int?
)
