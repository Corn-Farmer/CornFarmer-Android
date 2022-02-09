package com.example.corn_farmer.src.my_comment

import com.example.corn_farmer.src.my_comment.model.getMyComment

interface MyCommentFragmentView {
    fun onGetMyCommentSuccess(response : getMyComment)
    fun onGetMyCommentFailure(message : String)
}