package com.corn.corn_farmer.src.detail

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.detail.model.CommentLikeRetrofitInterface
import com.corn.corn_farmer.src.detail.model.getCommentLike
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentLikeService(var view : DetailFragmentView, var reviewIdx : Int, var token : String) {
    fun tryPutCommetLike() {
        val retrofitInterface = Application.sRetrofit.create(CommentLikeRetrofitInterface::class.java)
        retrofitInterface.getCommentLike(reviewIdx, token)
            .enqueue(object : Callback<getCommentLike>{
                override fun onResponse(
                    call: Call<getCommentLike>,
                    response: Response<getCommentLike>
                ) {
                    view.onPutCommentLikeSuccess(response.body() as getCommentLike)
                }

                override fun onFailure(call: Call<getCommentLike>, t: Throwable) {
                    view.onPutCommentLikeFailure(t.message ?: "통신 오류")
                }
            })
    }
}