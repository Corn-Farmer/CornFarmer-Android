package com.corn.corn_farmer.src.detail.model

import com.corn.corn_farmer.src.comment.model.sendReviewAPI
import retrofit2.Call
import retrofit2.http.*

interface DeclationUserRetrofitInterface {
    @POST("/reviews/{reviewIdx}/report")
    fun sendDeclationUser(
        @Path("reviewIdx") reviewIdx:Int,
        @Body sendDeclationAPI: sendDeclationAPI,
        @Header("X-ACCESS-TOKEN") token : String) : Call<getDeclationUserAPI>
}