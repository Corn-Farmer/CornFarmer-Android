package com.example.corn_farmer.src.home

import com.example.corn_farmer.src.home.model.MovieResponse

interface HomeFragmentView {
    fun onGetMovieListSuccess(response: MovieResponse)
    fun onGetMovieListFailure(message : String)
}