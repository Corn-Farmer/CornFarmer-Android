package com.corn.corn_farmer.src.my_comment.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MyCommentRetrofitInterface {
    @GET("/users/{userIdx}/reviews")
    fun getMyComment(@Path("userIdx") userIdx : Int, @Query("sort") sort : String, @Header ("X-ACCESS-TOKEN") token : String) : Call<getMyComment>
}