package com.example.corn_farmer.src.kakao

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.kakao.model.KakaoResponse
import com.example.corn_farmer.src.kakao.model.KakaoRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoService(var kakaoView: KakaoView, var accessToken: String) {

    fun tryGetUserInfo(){
        val retrofitInterface = Application.sRetrofit.create(KakaoRetrofitInterface::class.java)
        retrofitInterface.getUserInfo(accessToken).enqueue(object : Callback<KakaoResponse>{
            override fun onResponse(call: Call<KakaoResponse>, response: Response<KakaoResponse>) {

                kakaoView.onKakaoLoginSuccess(response.body() as KakaoResponse)



            }

            override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                kakaoView.onKakaoLoginFailure(t.message?:"통신오류")
            }

        })
    }

}