package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseKakao
import org.cornfarmer.data.service.KakaoRetrofitInterface
import org.cornfarmer.data.view.KakaoView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoService(
    var view: KakaoView,
    var sendKakaoAPI: org.cornfarmer.data.model.request.RequestKakao
) {
    fun tryPostToken() {
        val retrofitInterface = Application.sRetrofit.create(KakaoRetrofitInterface::class.java)
        retrofitInterface.sendToken(sendKakaoAPI).enqueue(object : Callback<ResponseKakao?> {
            override fun onResponse(call: Call<ResponseKakao?>, response: Response<ResponseKakao?>) {
                response.body()?.let { view.onPostTokenSuccess(it) }
            }

            override fun onFailure(call: Call<ResponseKakao?>, t: Throwable) {
                view.onPostTokenFailure(t.message ?: "통신오류")
            }
        })
    }
}
