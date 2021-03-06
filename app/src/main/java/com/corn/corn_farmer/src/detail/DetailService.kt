package com.corn.corn_farmer.src.detail

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.detail.model.DetailRetrofitInterface
import com.corn.corn_farmer.src.detail.model.getMovieDetailAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailService(var view : DetailFragmentView, var movieIdx : Int,var sort : String,var token : String) {
    fun tryGetMovieInfo() {
        val retrofitInterface = Application.sRetrofit.create(DetailRetrofitInterface::class.java)
        retrofitInterface.getMovieInfo(movieIdx, sort,token)
            .enqueue(object : Callback<getMovieDetailAPI> {
                override fun onResponse(
                    call: Call<getMovieDetailAPI>,
                    response: Response<getMovieDetailAPI>
                ) {
                    view.onGetDetailSuccess(response.body() as getMovieDetailAPI)
                }

                override fun onFailure(call: Call<getMovieDetailAPI>, t: Throwable) {
                    view.onGetDetailFailure(t.message ?: "통신 오류")
                }
            })
    }
}