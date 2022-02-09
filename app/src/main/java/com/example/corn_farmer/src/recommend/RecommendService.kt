package com.example.corn_farmer.src.recommend


import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.recommend.model.RecommendRetrofitInterface
import com.example.corn_farmer.src.recommend.model.getRecommendMovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendService(var view : RecommendFragmentView, var keywordIdx : Int, var token : String) {
    fun tryGetMovieInfo(){
        val retrofitInterface = Application.sRetrofit.create(RecommendRetrofitInterface::class.java)
        retrofitInterface.getRecommendInfo(keywordIdx, token).enqueue(object : Callback<getRecommendMovieAPI> {
            override fun onResponse(call: Call<getRecommendMovieAPI>, response: Response<getRecommendMovieAPI>) {
                view.onGetRecommendSuccess(response.body() as getRecommendMovieAPI)
            }

            override fun onFailure(call: Call<getRecommendMovieAPI>, t: Throwable) {
                view.onGetRecommendFailure(t.message?:"통신 오류")
            }
        })
    }
}