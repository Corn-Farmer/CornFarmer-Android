package org.cornfarmer.data.service

import org.cornfarmer.data.model.request.RequestNaver
import org.cornfarmer.data.model.response.ResponseNaver
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NaverRetrofitInterface {
    @POST("/users/oauth/naver")
    fun sendToken(@Body sendNaverAPI: RequestNaver): Call<ResponseNaver>
}
