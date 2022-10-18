package org.cornfarmer.data.repository

import org.cornfarmer.data.service.SearchResultRetrofitInterface
import org.cornfarmer.data.view.SearchResultFragmentView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultService(
    var view: SearchResultFragmentView,
    var keyword: String?,
    var sort: String,
    var token: String
) {
    fun tryGetSearchResultList() {
        val retrofitInterface =
            Application.sRetrofit.create(SearchResultRetrofitInterface::class.java)
        retrofitInterface.getSearchInfo(keyword, sort, token)
            .enqueue(object : Callback<org.cornfarmer.data.model.response.ResponseSearchResult> {
                override fun onResponse(
                    call: Call<org.cornfarmer.data.model.response.ResponseSearchResult>,
                    response: Response<org.cornfarmer.data.model.response.ResponseSearchResult>
                ) {
                    view.onGetSearchResultSuccess(response.body() as org.cornfarmer.data.model.response.ResponseSearchResult)
                }

                override fun onFailure(
                    call: Call<org.cornfarmer.data.model.response.ResponseSearchResult>,
                    t: Throwable
                ) {
                    view.onGetSearchResultFailure(t.message ?: "통신 오류")
                }
            })
    }
}
