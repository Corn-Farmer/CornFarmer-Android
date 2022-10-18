package org.cornfarmer.data.service

import org.cornfarmer.data.model.response.ResponseRecommend
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RecommendRetrofitInterface {
    @GET("/movies/keywords/{keywordIdx}")
    fun getRecommendInfo(
        @Path("keywordIdx") keywordIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ResponseRecommend>
}
