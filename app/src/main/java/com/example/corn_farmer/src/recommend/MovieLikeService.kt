package com.example.corn_farmer.src.recommend

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.recommend.model.movieLikeRetrofitInterface
import com.example.corn_farmer.src.recommend.model.putMovieLike
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieLikeService(var view : RecommendFragmentView, var movieIdx : Int, var token : String) {
    fun tryPutMovieLike() {
        val retrofitInterface = Application.sRetrofit.create(movieLikeRetrofitInterface::class.java)
        retrofitInterface.putMovieLike(movieIdx, token)
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