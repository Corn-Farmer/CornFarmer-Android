package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.KeywordResponse

interface KeywordFragmentView {
    fun onGetKeywordListSuccess(response: KeywordResponse)
    fun onGetKeywordListFailure(message: String)
}
