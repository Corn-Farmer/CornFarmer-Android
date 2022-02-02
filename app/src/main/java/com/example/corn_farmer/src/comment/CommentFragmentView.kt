package com.example.corn_farmer.src.comment

import com.example.corn_farmer.src.comment.model.getReviewAPI

interface CommentFragmentView {
    fun onPostReviewSuccess(response: getReviewAPI)
    fun onPostReviewFailure(message : String)
}