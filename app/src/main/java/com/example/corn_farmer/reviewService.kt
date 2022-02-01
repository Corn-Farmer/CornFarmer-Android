package com.example.corn_farmer

import com.example.corn_farmer.Item.getReviewAPI
import com.example.corn_farmer.Item.sendReviewAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface reviewService {
    @POST("/reviews")
    fun sendReview(@Body sendReviewAPI: sendReviewAPI) : Call<getReviewAPI>
}