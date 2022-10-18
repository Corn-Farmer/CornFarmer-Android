package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseSearchResult

interface SearchResultFragmentView {
    fun onGetSearchResultSuccess(response: ResponseSearchResult)
    fun onGetSearchResultFailure(message: String)
}
