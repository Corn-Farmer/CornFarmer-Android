package com.example.corn_farmer.src.join

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.join.model.JoinRetrofitInterface
import com.example.corn_farmer.src.join.model.getJoinAPI
import com.example.corn_farmer.src.join.model.sendJoinAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinService(var view: JoinView, var sendJoinAPI: sendJoinAPI) {
    fun tryPostJoin(){
        val retrofitInterface = Application.sRetrofit.create(JoinRetrofitInterface::class.java)
        retrofitInterface.sendJoin(sendJoinAPI).enqueue(object : Callback<getJoinAPI>{
            override fun onResponse(call: Call<getJoinAPI>, response: Response<getJoinAPI>) {
                view.onPostJoinSuccess(response.body() as getJoinAPI)
            }

            override fun onFailure(call: Call<getJoinAPI>, t: Throwable) {
                view.onPostJoinFailure(t.message?:"통신오류")
            }

        })
    }
}