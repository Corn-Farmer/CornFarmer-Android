package org.cornfarmer.data.service

import org.cornfarmer.data.model.request.RequestModifyComment
import org.cornfarmer.data.model.response.ResponseCommentModify
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface MyCommentModifyRetrofitInterface {
    @PUT("/reviews/{reviewIdx}")
    fun putMyCommentModify(
        @Body sendModifyComment: RequestModifyComment,
        @Path("reviewIdx") reviewIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ResponseCommentModify>
}
