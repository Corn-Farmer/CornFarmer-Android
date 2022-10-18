package org.cornfarmer.data.service

import org.cornfarmer.data.model.response.ResponseCommentLike
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentLikeRetrofitInterface {
    @PUT("/reviews/{reviewIdx}/like")
    fun getCommentLike(
        @Path("reviewIdx") reviewIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ResponseCommentLike>
}
