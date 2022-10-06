package com.corn.corn_farmer.src.home

import com.corn.corn_farmer.src.home.model.MovieResponse

interface HomeFragmentView {
    fun onGetMovieListSuccess(response: MovieResponse)
    fun onGetMovieListFailure(message : String)
}