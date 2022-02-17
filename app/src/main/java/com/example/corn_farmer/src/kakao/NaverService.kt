package com.example.corn_farmer.src.kakao

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.kakao.model.NaverRetrofitInterface
import com.example.corn_farmer.src.kakao.model.getNaverAPI
import com.example.corn_farmer.src.kakao.model.sendNaverAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NaverService (var view: NaverView, var sendNaverAPI: sendNaverAPI){
    fun tryPostToken(){
        val retrofitInterface = Application.sRetrofit.create(NaverRetrofitInterface::class.java)
        retrofitInterface.sendToken(sendNaverAPI).enqueue(object : Callback<getNaverAPI?> {

            override fun onResponse(call: Call<getNaverAPI?>, response: Response<getNaverAPI?>) {
                response.body()?.let { view.onPostNaverSuccess(it) }
            }

            override fun onFailure(call: Call<getNaverAPI?>, t: Throwable) {
                view.onPostNAverFailure(t.message?:"통신오류")
            }


        })

    }
}