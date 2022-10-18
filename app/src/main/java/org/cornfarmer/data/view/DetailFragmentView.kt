package org.cornfarmer.data.view

import org.cornfarmer.data.model.request.RequestMovieLike
import org.cornfarmer.data.model.response.ResponseCommentLike
import org.cornfarmer.data.model.response.ResponseMovieDetail

interface DetailFragmentView {
    fun onGetDetailSuccess(response: ResponseMovieDetail)
    fun onGetDetailFailure(message: String)
    fun onPutCommentLikeSuccess(response: ResponseCommentLike)
    fun onPutCommentLikeFailure(message: String)
    fun onPutMovieLikeSuccess(response: RequestMovieLike)
    fun onPutMovieLikeFailure(message: String)
}
