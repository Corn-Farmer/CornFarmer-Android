package com.example.corn_farmer.src.search_result

import com.example.corn_farmer.config.Application
import com.example.corn_farmer.src.search_result.model.SearchResultResponse
import com.example.corn_farmer.src.search_result.model.SearchResultRetrofitInterface
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class SearchResultService(var view : SearchResultFragmentView,var keyword : String?,var sort: String) {
    fun tryGetSearchResultList(){
        val retrofitInterface = Application.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        retrofitInterface.getSearchInfo(keyword,sort).enqueue(object : Callback<SearchResultResponse>{
            override fun onResponse(
                call: Call<SearchResultResponse>,
                response: Response<SearchResultResponse>
            ) {
                view.onGetSearchResultSuccess(response.body() as SearchResultResponse)
            }

            override fun onFailure(call: Call<SearchResultResponse>, t: Throwable) {
                view.onGetSearchResultFailure(t.message?:"통신 오류")
            }
        })


        }

}