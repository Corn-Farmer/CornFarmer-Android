package com.example.corn_farmer.src.keyword

import com.example.corn_farmer.src.keyword.model.KeywordResponse

interface KeywordFragmentView {
    fun onGetKeywordListSuccess(response: KeywordResponse)
    fun onGetKeywordListFailure(message : String)
}