package org.cornfarmer.data.service

import org.cornfarmer.data.model.request.RequestReview
import org.cornfarmer.data.model.response.ResponseReview
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReviewRetrofitInterface {
    @POST("/reviews")
    fun sendReview(
        @Body sendReviewAPI: RequestReview,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ResponseReview>
}
