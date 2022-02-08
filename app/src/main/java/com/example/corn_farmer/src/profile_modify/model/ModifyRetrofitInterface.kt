package com.example.corn_farmer.src.profile_modify.model

import com.example.corn_farmer.src.profile.model.ModifyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ModifyRetrofitInterface {

    @Multipart
    @POST("/users/{userIdx}")
    fun putModify(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("userIdx")userIdx : Int,
        @Part file: MultipartBody.Part,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>): Call<ModifyResponse?>
}