package com.corn.corn_farmer.src.kakao

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.kakao.model.KakaoRetrofitInterface
import com.corn.corn_farmer.src.kakao.model.getKakaoAPI
import com.corn.corn_farmer.src.kakao.model.sendKakaoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoService (var view: KakaoView, var sendKakaoAPI: sendKakaoAPI){
    fun tryPostToken(){
        val retrofitInterface = Application.sRetrofit.create(KakaoRetrofitInterface::class.java)
        retrofitInterface.sendToken(sendKakaoAPI).enqueue(object : Callback<getKakaoAPI?>{
            override fun onResponse(call: Call<getKakaoAPI?>, response: Response<getKakaoAPI?>) {
                response.body()?.let { view.onPostTokenSuccess(it) }
            }

            override fun onFailure(call: Call<getKakaoAPI?>, t: Throwable) {
                view.onPostTokenFailure(t.message?:"통신오류")
            }


        })

    }
}