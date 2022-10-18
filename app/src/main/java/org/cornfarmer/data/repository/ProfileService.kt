package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseProfile
import org.cornfarmer.data.service.ProfileRetrofitInterface
import org.cornfarmer.data.view.ProfileFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(var view: ProfileFragmentView, var userIdx: Int?, var serverToken: String?) {
    fun tryGetProfile() {
        val retrofitInterface = Application.sRetrofit.create(ProfileRetrofitInterface::class.java)
        retrofitInterface.getProfile(userIdx!!, serverToken!!)
            .enqueue(object : Callback<ResponseProfile> {
                override fun onResponse(
                    call: Call<ResponseProfile>,
                    response: Response<ResponseProfile>
                ) {
                    view.onGetProfileSuccess(response.body() as ResponseProfile)
                }

                override fun onFailure(
                    call: Call<ResponseProfile>,
                    t: Throwable
                ) {
                    view.onGetProfileFailure(t.message ?: "프로필-통신오류")
                }
            })
    }
}
