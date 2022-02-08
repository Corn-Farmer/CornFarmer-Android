package com.example.corn_farmer.src.profile

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.profile.model.ProfileResponse
import com.example.corn_farmer.src.profile.model.ProfileRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(var view : ProfileFragmentView,var userIdx : Int?,var serverToken : String?) {
    fun tryGetProfile(){
        val retrofitInterface = Application.sRetrofit.create(ProfileRetrofitInterface::class.java)
        retrofitInterface.getProfile(userIdx!!,serverToken!!).enqueue(object : Callback<ProfileResponse>{
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>
            ) {
                view.onGetProfileSuccess(response.body() as ProfileResponse)
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                view.onGetProfileFailure(t.message?:"프로필-통신오류")
            }


        })
    }
}