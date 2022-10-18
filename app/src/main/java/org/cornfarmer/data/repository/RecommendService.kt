package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseRecommend
import org.cornfarmer.data.service.RecommendRetrofitInterface
import org.cornfarmer.data.view.RecommendFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendService(var view: RecommendFragmentView, var keywordIdx: Int, var token: String) {
    fun tryGetMovieInfo() {
        val retrofitInterface = Application.sRetrofit.create(RecommendRetrofitInterface::class.java)
        retrofitInterface.getRecommendInfo(keywordIdx, token)
            .enqueue(object : Callback<ResponseRecommend> {
                override fun onResponse(
                    call: Call<ResponseRecommend>,
                    response: Response<ResponseRecommend>
                ) {
                    view.onGetRecommendSuccess(response.body() as ResponseRecommend)
                }

                override fun onFailure(call: Call<ResponseRecommend>, t: Throwable) {
                    view.onGetRecommendFailure(t.message ?: "통신 오류")
                }
            })
    }
}
