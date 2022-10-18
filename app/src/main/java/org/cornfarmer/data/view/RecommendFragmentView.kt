package org.cornfarmer.data.view

import org.cornfarmer.data.model.request.RequestMovieLike
import org.cornfarmer.data.model.response.ResponseRecommend

interface RecommendFragmentView {
    fun onGetRecommendSuccess(response: ResponseRecommend)
    fun onGetRecommendFailure(message: String)
    fun onPutMovieLikeSuccess(response: RequestMovieLike)
    fun onPutMovieLikeFailure(message: String)
}
