package com.corn.corn_farmer.src.keyword

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.keyword.model.KeywordResponse
import com.corn.corn_farmer.src.keyword.model.KeywordRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KeywordService(var view : KeywordFragmentView) {
    fun tryGetKeywordList(){
        val retrofitInterface = Application.sRetrofit.create(KeywordRetrofitInterface::class.java)
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