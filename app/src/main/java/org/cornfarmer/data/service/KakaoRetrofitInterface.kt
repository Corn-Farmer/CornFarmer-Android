package org.cornfarmer.data.service

import org.cornfarmer.data.model.request.RequestKakao
import org.cornfarmer.data.model.response.ResponseKakao
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoRetrofitInterface {
    @POST("/users/oauth/kakao")
    fun sendToken(@Body sendKakaoAPI: RequestKakao): Call<ResponseKakao>
}
