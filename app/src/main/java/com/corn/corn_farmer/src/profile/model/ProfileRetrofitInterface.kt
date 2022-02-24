package com.corn.corn_farmer.src.profile.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProfileRetrofitInterface {
    @GET("users/{userIdx}")
    fun getProfile(@Path("userIdx")userIdx : Int, @Header("X-ACCESS-TOKEN")token:String):Call<ProfileResponse>

}