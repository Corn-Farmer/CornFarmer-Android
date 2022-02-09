package com.example.corn_farmer.src.profile

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.profile.model.DeleteResponse
import com.example.corn_farmer.src.profile.model.DeleteRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteService(var view:DeleteView,var userIdx : Int?,var serverToken : String?) {
    fun tryPutDeleteUser(){
        val retrofitInterface = Application.sRetrofit.create(DeleteRetrofitInterface::class.java)
        retrofitInterface.deleteUser(userIdx!!,serverToken!! ).enqueue(object : Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                view.onPutDeleteSuccess(response.body() as DeleteResponse)
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                view.onPutDeleteFailure(t.message?:"삭제통신오류")
            }


        } )
    }
}