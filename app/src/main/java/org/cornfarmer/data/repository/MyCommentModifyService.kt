package org.cornfarmer.data.repository

import org.cornfarmer.data.model.request.RequestModifyComment
import org.cornfarmer.data.model.response.ResponseCommentModify
import org.cornfarmer.data.service.MyCommentModifyRetrofitInterface
import org.cornfarmer.data.view.MyCommentModifyFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCommentModifyService(
    var view: MyCommentModifyFragmentView,
    private var sendModifyComment: RequestModifyComment,
    var reviewIdx: Int,
    var token: String
) {
    fun tryPutMyCommentModify() {
        val retrofitInterface =
            Application.sRetrofit.create(MyCommentModifyRetrofitInterface::class.java)
        retrofitInterface.putMyCommentModify(sendModifyComment, reviewIdx, token)
            .enqueue(object : Callback<ResponseCommentModify> {
                override fun onResponse(
                    call: Call<ResponseCommentModify>,
                    response: Response<ResponseCommentModify>
                ) {
                    view.onPutMyCommentModifySuccess(response.body() as ResponseCommentModify)
                }

                override fun onFailure(
                    call: Call<ResponseCommentModify>,
                    t: Throwable
                ) {
                    view.onPutMyCommentModifyFailure(t.message ?: "통신 오류")
                }
            })
    }
}
