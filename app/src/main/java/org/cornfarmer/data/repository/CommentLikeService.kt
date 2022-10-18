package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseCommentLike
import org.cornfarmer.data.service.CommentLikeRetrofitInterface
import org.cornfarmer.data.view.DetailFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentLikeService(var view: DetailFragmentView, var reviewIdx: Int, var token: String) {
    fun tryPutCommetLike() {
        val retrofitInterface =
            Application.sRetrofit.create(CommentLikeRetrofitInterface::class.java)
        retrofitInterface.getCommentLike(reviewIdx, token)
            .enqueue(object : Callback<ResponseCommentLike> {
                override fun onResponse(
                    call: Call<ResponseCommentLike>,
                    response: Response<ResponseCommentLike>
                ) {
                    view.onPutCommentLikeSuccess(response.body() as ResponseCommentLike)
                }

                override fun onFailure(call: Call<ResponseCommentLike>, t: Throwable) {
                    view.onPutCommentLikeFailure(t.message ?: "통신 오류")
                }
            })
    }
}
