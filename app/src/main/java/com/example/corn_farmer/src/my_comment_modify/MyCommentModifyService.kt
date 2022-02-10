package com.example.corn_farmer.src.my_comment_modify

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.my_comment_modify.model.MyCommentModifyRetrofitInterface
import com.example.corn_farmer.src.my_comment_modify.model.getCommnetModifyAPI
import com.example.corn_farmer.src.my_comment_modify.model.sendModifyComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MyCommentModifyService(var view : MyCommentModifyFragmentView, var sendModifyComment: sendModifyComment, var reviewIdx : Int, var token : String) {
    fun tryPutMyCommentModify() {
        val retrofitInterface = Application.sRetrofit.create(MyCommentModifyRetrofitInterface::class.java)
        retrofitInterface.putMyCommentModify(sendModifyComment, reviewIdx, token)
            .enqueue(object : Callback<getCommnetModifyAPI> {
                override fun onResponse(
                    call: Call<getCommnetModifyAPI>,
                    response: Response<getCommnetModifyAPI>
                ) {
                    view.onPutMyCommentModifySuccess(response.body() as getCommnetModifyAPI)
                }

                override fun onFailure(call: Call<getCommnetModifyAPI>, t: Throwable) {
                    view.onPutMyCommentModifyFailure(t.message?:"통신 오류")
                }
            })
    }
}