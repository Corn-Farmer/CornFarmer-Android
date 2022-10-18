package org.cornfarmer.data.service

import org.cornfarmer.data.model.response.ModifyProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ModifyProfileRetrofitInterface {
    @GET("users/{userIdx}/info")
    fun getModifyProfile(
        @Path("userIdx") userIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ModifyProfileResponse>
}
