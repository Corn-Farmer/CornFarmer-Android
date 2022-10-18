package org.cornfarmer.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.cornfarmer.data.model.response.ResponseJoin
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface JoinRetrofitInterface {

    @Multipart
    @POST("/users/")
    fun sendJoin(
        @Header("X-ACCESS-TOKEN") token: String,
        @Part file: MultipartBody.Part?,
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<ResponseJoin?>
}
