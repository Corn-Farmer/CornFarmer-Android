package com.corn.corn_farmer.src.comment.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ReviewRetrofitInterface {
    @POST("/reviews")
    fun sendReview(@Body sendReviewAPI: sendReviewAPI,
    @Header("X-ACCESS-TOKEN") token : String) : Call<getReviewAPI>
}