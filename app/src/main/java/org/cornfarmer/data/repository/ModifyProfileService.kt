package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ModifyProfileResponse
import org.cornfarmer.data.service.ModifyProfileRetrofitInterface
import org.cornfarmer.data.view.ModifyView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyProfileService(var view: ModifyView, var userIdx: Int?, var serverToken: String?) {
    fun tryGetModifyProfile() {
        val retrofitInterface =
            Application.sRetrofit.create(ModifyProfileRetrofitInterface::class.java)
        retrofitInterface.getModifyProfile(userIdx!!, serverToken!!)
            .enqueue(object : Callback<ModifyProfileResponse> {
                override fun onResponse(
                    call: Call<ModifyProfileResponse>,
                    response: Response<ModifyProfileResponse>
                ) {
                    view.onGetModifyProfileSuccess(response.body() as ModifyProfileResponse)
                }

                override fun onFailure(call: Call<ModifyProfileResponse>, t: Throwable) {
                    view.onGetModifyProfileFailure(t.message ?: "통신오류")
                }
            })
    }
}
