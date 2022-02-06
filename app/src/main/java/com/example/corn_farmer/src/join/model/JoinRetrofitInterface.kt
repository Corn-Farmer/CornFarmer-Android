package com.example.corn_farmer.src.join.model

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody

import retrofit2.http.POST

import retrofit2.http.Multipart

interface JoinRetrofitInterface {

    @Multipart
    @POST("/users")
    fun sendJoin(
        @Header("X-ACCESS-TOKEN") token: String,
        @Part file: MultipartBody.Part,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>): Call<getJoinAPI?>
}