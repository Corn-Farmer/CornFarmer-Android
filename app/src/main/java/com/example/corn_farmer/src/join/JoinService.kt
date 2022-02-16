package com.example.corn_farmer.src.join

import android.util.Log
import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.join.model.JoinRetrofitInterface
import com.example.corn_farmer.src.join.model.getJoinAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinService(var view: JoinView,var token: String, var file: MultipartBody.Part?, var params: Map<String, RequestBody>) {
    fun tryPostJoin(){

            val retrofitInterface = Application.sRetrofit.create(JoinRetrofitInterface::class.java)
            retrofitInterface.sendJoin(token, file, params).enqueue(object : Callback<getJoinAPI?>{

                override fun onResponse(call: Call<getJoinAPI?>, response: Response<getJoinAPI?>) {
                    Log.d("LEE", response.message())
                    response.body()?.let { view.onPostJoinSuccess(it) }
                }

                override fun onFailure(call: Call<getJoinAPI?>, t: Throwable) {

                    Log.d("LEE", t.toString())
                    view.onPostJoinFailure(t.message?:"통신오류")
                }

            })




    }
}