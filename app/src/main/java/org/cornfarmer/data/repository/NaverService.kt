package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseNaver
import org.cornfarmer.data.service.NaverRetrofitInterface
import org.cornfarmer.data.view.NaverView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NaverService(
    var view: NaverView,
    var sendNaverAPI: org.cornfarmer.data.model.request.RequestNaver
) {
    fun tryPostToken() {
        val retrofitInterface = Application.sRetrofit.create(NaverRetrofitInterface::class.java)
        retrofitInterface.sendToken(sendNaverAPI).enqueue(object : Callback<ResponseNaver?> {

            override fun onResponse(call: Call<ResponseNaver?>, response: Response<ResponseNaver?>) {
                response.body()?.let { view.onPostNaverSuccess(it) }
            }

            override fun onFailure(call: Call<ResponseNaver?>, t: Throwable) {
                view.onPostNAverFailure(t.message ?: "통신오류")
            }
        })
    }
}
