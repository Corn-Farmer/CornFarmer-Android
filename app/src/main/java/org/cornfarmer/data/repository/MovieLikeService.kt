package org.cornfarmer.data.repository

import org.cornfarmer.data.model.request.RequestMovieLike
import org.cornfarmer.data.service.MovieLikeRetrofitInterface
import org.cornfarmer.data.view.DetailFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieLikeService(var view: DetailFragmentView, var movieIdx: Int?, var token: String?) {
    fun tryPutMovieLike() {
        val retrofitInterface = Application.sRetrofit.create(MovieLikeRetrofitInterface::class.java)
        retrofitInterface.putMovieLike(movieIdx!!, token!!)
            .enqueue(object : Callback<RequestMovieLike> {
                override fun onResponse(
                    call: Call<RequestMovieLike>,
                    response: Response<RequestMovieLike>
                ) {
                    view.onPutMovieLikeSuccess(response.body() as RequestMovieLike)
                }

                override fun onFailure(call: Call<RequestMovieLike>, t: Throwable) {
                    view.onPutMovieLikeFailure(t.message ?: "통신 오류")
                }
            })
    }
}
