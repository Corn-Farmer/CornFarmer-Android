package com.example.corn_farmer.src.home

import com.example.corn_farmer.src.home.model.MovieResponse
import com.example.corn_farmer.src.home.model.MovieRetrofitInterface
import com.example.corn_farmer.config.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(var view : HomeFragmentView) {
    fun tryGetMovieList(){
        val retrofitInterface = Application.sRetrofit.create(MovieRetrofitInterface::class.java)
        retrofitInterface.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                view.onGetMovieListSuccess(response.body() as MovieResponse)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                view.onGetMovieListFailure(t.message?:"통신 오류")
            }
        })


    }
}