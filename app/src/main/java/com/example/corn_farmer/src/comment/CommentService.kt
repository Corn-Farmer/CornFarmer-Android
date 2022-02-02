package com.example.corn_farmer.src.comment

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.comment.model.ReviewRetrofitInterface
import com.example.corn_farmer.src.comment.model.getReviewAPI
import com.example.corn_farmer.src.comment.model.sendReviewAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentService(var view : CommentFragmentView,var sendReviewAPI: sendReviewAPI) {
    fun tryPostReview(){
        val retrofitInterface = Application.sRetrofit.create(ReviewRetrofitInterface::class.java)
        retrofitInterface.sendReview(sendReviewAPI).enqueue(object : Callback<getReviewAPI> {
            override fun onResponse(call: Call<getReviewAPI>, response: Response<getReviewAPI>) {
                view.onPostReviewSuccess(response.body() as getReviewAPI)
            }

            override fun onFailure(call: Call<getReviewAPI>, t: Throwable) {
                view.onPostReviewFailure(t.message?:"통신 오류")
            }
        })


    }
}