package com.corn.corn_farmer.src.profile_modify

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.profile.model.ModifyResponse
import com.corn.corn_farmer.src.profile_modify.model.ModifyRetrofitInterface
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ModifyService(var view: ModifyView, var token: String, var file: MultipartBody.Part, var params: Map<String, RequestBody>,var userIdx : Int) {
    fun tryPutModify(){
        val retrofitInterface = Application.sRetrofit.create(ModifyRetrofitInterface::class.java)
        retrofitInterface.putModify(token,userIdx,file, params).enqueue(object : Callback<ModifyResponse?>{

            override fun onResponse(
                call: Call<ModifyResponse?>,
                response: Response<ModifyResponse?>
            ) {
                response.body()?.let { view.onPutModifySuccess(it) }
            }

            override fun onFailure(call: Call<ModifyResponse?>, t: Throwable) {
                view.onPutModifyFailure(t.message?:"통신오류")
            }

        })
    }
}