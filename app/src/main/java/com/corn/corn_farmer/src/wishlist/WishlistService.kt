package com.corn.corn_farmer.src.wishlist

import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.wishlist.model.getWishMovie
import com.corn.corn_farmer.src.wishlist.model.getWishlistMovieRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistService(var view : WishlistView, var userIdx : Int, var token : String) {
    fun tryGetWishlist() {
        val retrofitInterface = Application.sRetrofit.create(getWishlistMovieRetrofitInterface::class.java)
        retrofitInterface.getWishlist(userIdx, token).enqueue(object : Callback<getWishMovie> {
            override fun onResponse(call: Call<getWishMovie>, response: Response<getWishMovie>) {
                view.onGetWishlistSuccess(response.body() as getWishMovie)
            }

            override fun onFailure(call: Call<getWishMovie>, t: Throwable) {
                view.onGetWishlistFailure(t.message?:"통신 오류")
            }
        })
    }
}