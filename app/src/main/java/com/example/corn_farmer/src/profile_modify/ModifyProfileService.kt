package com.example.corn_farmer.src.profile_modify

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.profile.ProfileFragmentView
import com.example.corn_farmer.src.profile_modify.model.ModifyProfileResponse
import com.example.corn_farmer.src.profile_modify.model.ModifyProfileRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyProfileService(var view : ModifyView, var userIdx : Int?, var serverToken : String?) {
    fun tryGetModifyProfile(){
        val retrofitInterface = Application.sRetrofit.create(ModifyProfileRetrofitInterface::class.java)
        retrofitInterface.getModifyProfile(userIdx!!,serverToken!!).enqueue(object : Callback<ModifyProfileResponse>{
            override fun onResponse(
                call: Call<ModifyProfileResponse>,
                response: Response<ModifyProfileResponse>
            ) {
                view.onGetModifyProfileSuccess(response.body() as ModifyProfileResponse)
            }

            override fun onFailure(call: Call<ModifyProfileResponse>, t: Throwable) {
                view.onGetModifyProfileFailure(t.message?:"통신오류")
            }

        })
    }
}