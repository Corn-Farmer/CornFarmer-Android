package com.corn.corn_farmer.src.detail.model

import retrofit2.Call
import retrofit2.http.*

interface DeclationUserRetrofitInterface {
    @POST("/reviews/{reviewIdx}/report")
    fun sendDeclationUser(
        @Path("reviewIdx") reviewIdx:Int,
        @Body report : String,
        @Header("X-ACCESS-TOKEN") token : String) : Call<getDeclationUserAPI>
}