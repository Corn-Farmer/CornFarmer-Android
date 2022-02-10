package com.example.corn_farmer.src.my_comment_modify.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface MyCommentModifyRetrofitInterface {
    @PUT("/reviews/{reviewIdx}")
    fun putMyCommentModify(@Body sendModifyComment: sendModifyComment, @Path("reviewIdx") reviewIdx : Int, @Header("X-ACCESS-TOKEN") token : String) : Call<getCommnetModifyAPI>
}