package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseWishMovie
import org.cornfarmer.data.service.GetWishlistMovieRetrofitInterface
import org.cornfarmer.data.view.WishlistView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistService(var view: WishlistView, var userIdx: Int, var token: String) {
    fun tryGetWishlist() {
        val retrofitInterface =
            Application.sRetrofit.create(GetWishlistMovieRetrofitInterface::class.java)
        retrofitInterface.getWishlist(userIdx, token).enqueue(object : Callback<ResponseWishMovie> {
            override fun onResponse(call: Call<ResponseWishMovie>, response: Response<ResponseWishMovie>) {
                view.onGetWishlistSuccess(response.body() as ResponseWishMovie)
            }

            override fun onFailure(call: Call<ResponseWishMovie>, t: Throwable) {
                view.onGetWishlistFailure(t.message ?: "통신 오류")
            }
        })
    }
}
