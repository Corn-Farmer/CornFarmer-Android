package org.cornfarmer.data.repository

import org.cornfarmer.data.service.DeleteRetrofitInterface
import org.cornfarmer.data.view.DeleteView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteService(var view: DeleteView, var userIdx: Int?, var serverToken: String?) {
    fun tryPutDeleteUser() {
        val retrofitInterface = Application.sRetrofit.create(DeleteRetrofitInterface::class.java)
        retrofitInterface.deleteUser(userIdx!!, serverToken!!)
            .enqueue(object : Callback<org.cornfarmer.data.model.response.ResponseDelete> {
                override fun onResponse(
                    call: Call<org.cornfarmer.data.model.response.ResponseDelete>,
                    response: Response<org.cornfarmer.data.model.response.ResponseDelete>
                ) {
                    view.onPutDeleteSuccess(response.body() as org.cornfarmer.data.model.response.ResponseDelete)
                }

                override fun onFailure(
                    call: Call<org.cornfarmer.data.model.response.ResponseDelete>,
                    t: Throwable
                ) {
                    view.onPutDeleteFailure(t.message ?: "삭제통신오류")
                }
            })
    }
}
