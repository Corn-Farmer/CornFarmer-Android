package com.corn.corn_farmer.src.profile.model

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeleteRetrofitInterface {
    @PUT("users/{userIdx}/delete")
    fun deleteUser(
        @Path("userIdx") userIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<DeleteResponse>
}