package org.cornfarmer.data.service

import org.cornfarmer.data.model.response.KeywordResponse
import retrofit2.Call
import retrofit2.http.GET

interface KeywordRetrofitInterface {
    @GET("movies/keywords")
    fun getKeywordList(): Call<KeywordResponse>
}
