package org.cornfarmer.data.service

import org.cornfarmer.data.model.response.ResponseWishMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetWishlistMovieRetrofitInterface {
    @GET("/users/{userIdx}/likes/movies")
    fun getWishlist(
        @Path("userIdx") userIdx: Int,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ResponseWishMovie>
}
