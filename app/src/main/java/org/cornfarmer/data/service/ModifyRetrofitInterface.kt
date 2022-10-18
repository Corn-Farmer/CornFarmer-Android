package org.cornfarmer.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.cornfarmer.data.model.response.ModifyResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ModifyRetrofitInterface {

    @Multipart
    @POST("/users/{userIdx}")
    fun putModify(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("userIdx") userIdx: Int,
        @Part file: MultipartBody.Part,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<ModifyResponse?>
}
