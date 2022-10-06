package com.corn.corn_farmer.src.detail

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.comment.model.sendReviewAPI
import com.corn.corn_farmer.src.detail.model.DeclationUserRetrofitInterface
import com.corn.corn_farmer.src.detail.model.getDeclationUserAPI
import com.corn.corn_farmer.src.detail.model.sendDeclationAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeclationService(var view : DeclationView, var reviewIdx: Int, var sendDeclation: sendDeclationAPI, var token : String) {
    fun tryPostDeclation(){
        val retrofitInterface = Application.sRetrofit.create(DeclationUserRetrofitInterface::class.java)
        retrofitInterface.sendDeclationUser(reviewIdx, sendDeclation, token).enqueue(object : Callback<getDeclationUserAPI> {

            override fun onResponse(
                call: Call<getDeclationUserAPI>,
                response: Response<getDeclationUserAPI>
            ) {
                view.onPostDeclationSuccess(response.body() as getDeclationUserAPI)
            }

            override fun onFailure(call: Call<getDeclationUserAPI>, t: Throwable) {
                view.onPostDeclationFailure(t.message?:"통신 오류")
            }

        })


    }
}