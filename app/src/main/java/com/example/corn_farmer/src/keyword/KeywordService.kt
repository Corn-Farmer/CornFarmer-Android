package com.example.corn_farmer.src.keyword

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.home.model.MovieRetrofitInterface
import com.example.corn_farmer.src.keyword.model.KeywordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeywordService(var view : KeywordFragmentView) {
    fun tryGetKeywordList(){
        val retrofitInterface = Application.sRetrofit.create(MovieRetrofitInterface::class.java)
        retrofitInterface.getKeywordList().enqueue(object : Callback<KeywordResponse> {
            override fun onResponse(call: Call<KeywordResponse>, response: Response<KeywordResponse>) {
                view.onGetKeywordListSuccess(response.body() as KeywordResponse)
            }

            override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                view.onGetKeywordListFailure(t.message?:"통신 오류")
            }

        })


    }
}