package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseMovieDetail
import org.cornfarmer.data.service.DetailRetrofitInterface
import org.cornfarmer.data.view.DetailFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailService(
    var view: DetailFragmentView,
    var movieIdx: Int,
    var sort: String,
    var token: String
) {
    fun tryGetMovieInfo() {
        val retrofitInterface = Application.sRetrofit.create(DetailRetrofitInterface::class.java)
        retrofitInterface.getMovieInfo(movieIdx, sort, token)
            .enqueue(object : Callback<ResponseMovieDetail> {
                override fun onResponse(
                    call: Call<ResponseMovieDetail>,
                    response: Response<ResponseMovieDetail>
                ) {
                    view.onGetDetailSuccess(response.body() as ResponseMovieDetail)
                }

                override fun onFailure(call: Call<ResponseMovieDetail>, t: Throwable) {
                    view.onGetDetailFailure(t.message ?: "통신 오류")
                }
            })
    }
}
