package org.cornfarmer.data.service

import org.cornfarmer.data.model.request.RequestMovieLike
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovieLikeRetrofitInterface {
    @PUT("/movies/{movieIdx}/like")
    fun putMovieLike(
        @Path("movieIdx") movieIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<RequestMovieLike>
}
