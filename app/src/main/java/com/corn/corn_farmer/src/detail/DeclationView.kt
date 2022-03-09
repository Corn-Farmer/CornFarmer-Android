package com.corn.corn_farmer.src.detail

import com.corn.corn_farmer.src.comment.model.getReviewAPI
import com.corn.corn_farmer.src.detail.model.getDeclationResult
import com.corn.corn_farmer.src.detail.model.getDeclationUserAPI

interface DeclationView {
    fun onPostDeclationSuccess(response: getDeclationUserAPI)
    fun onPostDeclationFailure(message : String)
}