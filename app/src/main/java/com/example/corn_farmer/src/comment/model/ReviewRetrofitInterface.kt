package com.example.corn_farmer.src.comment.model

import com.example.corn_farmer.src.comment.model.getReviewAPI
import com.example.corn_farmer.src.comment.model.sendReviewAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ReviewRetrofitInterface {
    @POST("/reviews")
    fun sendReview(@Body sendReviewAPI: sendReviewAPI) : Call<getReviewAPI>
}