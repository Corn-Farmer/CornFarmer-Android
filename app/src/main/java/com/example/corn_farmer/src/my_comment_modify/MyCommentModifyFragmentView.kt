package com.example.corn_farmer.src.my_comment_modify

import com.example.corn_farmer.src.my_comment_modify.model.getCommnetModifyAPI

interface MyCommentModifyFragmentView {
    fun onPutMyCommentModifySuccess(response : getCommnetModifyAPI)
    fun onPutMyCommentModifyFailure(message : String)
}