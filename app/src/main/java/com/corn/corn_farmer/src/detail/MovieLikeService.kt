package com.corn.corn_farmer.src.detail

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.detail.model.movieLikeRetrofitInterface
import com.corn.corn_farmer.src.detail.model.putMovieLike
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieLikeService(var view : DetailFragmentView, var movieIdx : Int?, var token : String?) {
    fun tryPutMovieLike() {
        val retrofitInterface = Application.sRetrofit.create(movieLikeRetrofitInterface::class.java)
        retrofitInterface.putMovieLike(movieIdx!!, token!!)
            .enqueue(object : Callback<putMovieLike>{
                override fun onResponse(
                    call: Call<putMovieLike>,
                    response: Response<putMovieLike>
                ) {
                    view.onPutMovieLikeSuccess(response.body() as putMovieLike)
                }

                override fun onFailure(call: Call<putMovieLike>, t: Throwable) {
                    view.onPutMovieLikeFailure(t.message ?: "통신 오류")
                }
            })
    }
}