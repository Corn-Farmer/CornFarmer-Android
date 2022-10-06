package com.corn.corn_farmer.src.detail.model

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentLikeRetrofitInterface {
    @PUT("/reviews/{reviewIdx}/like")
    fun getCommentLike(
        @Path("reviewIdx") reviewIdx:Int,
    @Header ("X-ACCESS-TOKEN") token : String) : Call<getCommentLike>
}