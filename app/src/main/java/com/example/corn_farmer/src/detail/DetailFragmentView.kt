package com.example.corn_farmer.src.detail

import com.example.corn_farmer.src.detail.model.getMovieDetailAPI

interface DetailFragmentView {
    fun onGetDetailSuccess(response: getMovieDetailAPI)
    fun onGetDetailFailure(message : String)
}