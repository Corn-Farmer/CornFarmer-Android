package com.corn.corn_farmer.src.wishlist.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface getWishlistMovieRetrofitInterface {
    @GET("/users/{userIdx}/likes/movies")
    fun getWishlist(@Path("userIdx") userIdx:Int, @Header("X-ACCESS-TOKEN") token : String) : Call<getWishMovie>
}