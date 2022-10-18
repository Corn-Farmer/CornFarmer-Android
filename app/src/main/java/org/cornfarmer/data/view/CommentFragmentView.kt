package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseReview

interface CommentFragmentView {
    fun onPostReviewSuccess(response: ResponseReview)
    fun onPostReviewFailure(message: String)
}
