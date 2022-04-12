package com.corn.corn_farmer.src.search_result.model

import com.corn.corn_farmer.src.home.model.MovieDto
import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @SerializedName("isSuccess") var isSuccess : Boolean?,
    @SerializedName("code") var code : Int?,
    @SerializedName("message") var message : String?,
    @SerializedName("result") var result : List<MovieDto>?
)
