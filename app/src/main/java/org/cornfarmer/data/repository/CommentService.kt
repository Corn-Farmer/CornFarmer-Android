package org.cornfarmer.data.repository

import org.cornfarmer.data.model.request.RequestReview
import org.cornfarmer.data.model.response.ResponseReview
import org.cornfarmer.data.service.ReviewRetrofitInterface
import org.cornfarmer.data.view.CommentFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentService(
    var view: CommentFragmentView,
    var sendReviewAPI: RequestReview,
    var token: String
) {
    fun tryPostReview() {
        val retrofitInterface = Application.sRetrofit.create(ReviewRetrofitInterface::class.java)
        retrofitInterface.sendReview(sendReviewAPI, token)
            .enqueue(object : Callback<ResponseReview> {
                override fun onResponse(
                    call: Call<ResponseReview>,
                    response: Response<ResponseReview>
                ) {
                    view.onPostReviewSuccess(response.body() as ResponseReview)
                }

                override fun onFailure(
                    call: Call<ResponseReview>,
                    t: Throwable
                ) {
                    view.onPostReviewFailure(t.message ?: "통신 오류")
                }
            })
    }
}
