package com.corn.corn_farmer.src.comment

import com.corn.corn_farmer.src.comment.model.getReviewAPI

interface CommentFragmentView {
    fun onPostReviewSuccess(response: getReviewAPI)
    fun onPostReviewFailure(message : String)
}