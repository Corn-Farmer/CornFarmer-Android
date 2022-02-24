package com.corn.corn_farmer.src.detail

import com.corn.corn_farmer.src.detail.model.getCommentLike
import com.corn.corn_farmer.src.detail.model.getMovieDetailAPI
import com.corn.corn_farmer.src.detail.model.putMovieLike

interface DetailFragmentView {
    fun onGetDetailSuccess(response: getMovieDetailAPI)
    fun onGetDetailFailure(message : String)
    fun onPutCommentLikeSuccess(response : getCommentLike)
    fun onPutCommentLikeFailure(message: String)
    fun onPutMovieLikeSuccess(response: putMovieLike)
    fun onPutMovieLikeFailure(message: String)
}