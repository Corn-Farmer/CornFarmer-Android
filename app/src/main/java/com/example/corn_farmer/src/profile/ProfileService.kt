package com.example.corn_farmer.src.profile

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.profile.model.ProfileResponse
import com.example.corn_farmer.src.profile.model.ProfileRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(var view : ProfileFragmentView) {
    fun tryGetProfile(){
        val retrofitInterface = Application.sRetrofit.create(ProfileRetrofitInterface::class.java)
        retrofitInterface.getProfile(14,"eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyX2lkeCI6MTQsIm9hdXRoX2lkIjoiMjEwNjU2Mzc1MyIsImlhdCI6MTY0NDE2NDQ1OSwiZXhwIjoxNjQ1NjM1Njg4fQ.2qm0AiyKhO-F38iGeVhCt0Gm4vu9yQ6Bdpi6P4PZBdo").enqueue(object : Callback<ProfileResponse>{
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