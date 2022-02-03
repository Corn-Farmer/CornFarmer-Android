package com.example.corn_farmer.src.search_result

import com.example.corn_farmer.src.search_result.model.SearchResultResponse

interface SearchResultFragmentView {
    fun onGetSearchResultSuccess(response: SearchResultResponse)
    fun onGetSearchResultFailure(message : String)
}