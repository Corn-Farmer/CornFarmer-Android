package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseMyComment

interface MyCommentFragmentView {
    fun onGetMyCommentSuccess(response: ResponseMyComment)
    fun onGetMyCommentFailure(message: String)
}
