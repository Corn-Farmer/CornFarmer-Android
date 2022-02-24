package com.corn.corn_farmer.src.my_comment

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.my_comment.model.MyCommentRetrofitInterface
import com.corn.corn_farmer.src.my_comment.model.getMyComment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCommentService(var view : MyCommentFragmentView, var userIdx : Int, var sort : String, var token : String) {
    fun tryGetMyComment() {
        val retrofitInterface = Application.sRetrofit.create(MyCommentRetrofitInterface::class.java)
        retrofitInterface.getMyComment(userIdx, sort, token)
            .enqueue(object : Callback<getMyComment> {
                override fun onResponse(
                    call: Call<getMyComment>,
                    response: Response<getMyComment>
                ) {
                    view.onGetMyCommentSuccess(response.body() as getMyComment)
                }

                override fun onFailure(call: Call<getMyComment>, t: Throwable) {
                    view.onGetMyCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}