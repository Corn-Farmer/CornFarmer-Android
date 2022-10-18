package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseCommentModify

interface MyCommentModifyFragmentView {
    fun onPutMyCommentModifySuccess(response: ResponseCommentModify)
    fun onPutMyCommentModifyFailure(message: String)
}
