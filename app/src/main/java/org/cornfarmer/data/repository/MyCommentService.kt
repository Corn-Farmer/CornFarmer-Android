package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseMyComment
import org.cornfarmer.data.service.MyCommentRetrofitInterface
import org.cornfarmer.data.view.MyCommentFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCommentService(
    var view: MyCommentFragmentView,
    var userIdx: Int,
    var sort: String,
    var token: String
) {
    fun tryGetMyComment() {
        val retrofitInterface = Application.sRetrofit.create(MyCommentRetrofitInterface::class.java)
        retrofitInterface.getMyComment(userIdx, sort, token)
            .enqueue(object : Callback<ResponseMyComment> {
                override fun onResponse(
                    call: Call<ResponseMyComment>,
                    response: Response<ResponseMyComment>
                ) {
                    view.onGetMyCommentSuccess(response.body() as ResponseMyComment)
                }

                override fun onFailure(call: Call<ResponseMyComment>, t: Throwable) {
                    view.onGetMyCommentFailure(t.message ?: "통신 오류")
                }
            })
    }
}
