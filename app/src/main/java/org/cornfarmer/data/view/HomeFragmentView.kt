package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.MovieResponse

interface HomeFragmentView {
    fun onGetMovieListSuccess(response: MovieResponse)
    fun onGetMovieListFailure(message: String)
}
