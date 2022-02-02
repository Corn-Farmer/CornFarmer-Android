package com.example

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

class KakaoService {

    private lateinit var kakaoView: KakaoView
    var list: String

    fun setKakaoView(kakaoView: KakaoView){
        this.kakaoView = kakaoView
    }

    fun kLogin(accessToken: String){
        val kakaoService = getRetrofit().create(KakaoRetrofitInterface::class.java)

        kakaoView.onKakaoLoginLoading()

        kakaoService.kLogin(accessToken).enqueue(object : Callback<KakaoResponse>{

            override fun onResponse(call: Call<KakaoResponse>, response: Response<KakaoResponse>) {
                Log.d("KakaoLogin/RESPONSE", response.toString())

                val resp = response.body()!!

                Log.d("KakaoLogin/API-RESPONSE", resp.toString())


            }

            override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                Log.d("KakaoLogin/API-ERROR", t.toString())

                kakaoView.onKakaoLoginFailure(400, "네트워크 오류가 발생했습니다.")


            }

        })


    }

}