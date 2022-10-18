package org.cornfarmer.data.repository

import android.util.Log
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.cornfarmer.data.model.response.ResponseJoin
import org.cornfarmer.data.service.JoinRetrofitInterface
import org.cornfarmer.data.view.JoinView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinService(
    var view: JoinView,
    var token: String,
    var file: MultipartBody.Part?,
    var params: Map<String, RequestBody>
) {
    fun tryPostJoin() {
        val retrofitInterface = Application.sRetrofit.create(JoinRetrofitInterface::class.java)
        retrofitInterface.sendJoin(token, file, params).enqueue(object : Callback<ResponseJoin?> {

            override fun onResponse(call: Call<ResponseJoin?>, response: Response<ResponseJoin?>) {
                Log.d("JOIN", response.message())
                response.body()?.let { view.onPostJoinSuccess(it) }
            }

            override fun onFailure(call: Call<ResponseJoin?>, t: Throwable) {
                Log.d("JOIN", t.toString())
                view.onPostJoinFailure(t.message ?: "통신오류")
            }
        })
    }
}
