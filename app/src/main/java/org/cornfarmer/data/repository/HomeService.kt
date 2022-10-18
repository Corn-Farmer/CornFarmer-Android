package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.MovieResponse
import org.cornfarmer.data.service.MovieRetrofitInterface
import org.cornfarmer.data.view.HomeFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(var view: HomeFragmentView, var token: String?) {
    fun tryGetMovieList() {
        val retrofitInterface = Application.sRetrofit.create(MovieRetrofitInterface::class.java)
        retrofitInterface.getMovieList(token!!).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                view.onGetMovieListSuccess(response.body() as MovieResponse)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                view.onGetMovieListFailure(t.message ?: "통신 오류")
            }
        })
    }
}
