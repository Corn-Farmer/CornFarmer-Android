package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseWishMovie

interface WishlistView {
    fun onGetWishlistSuccess(response: ResponseWishMovie)
    fun onGetWishlistFailure(message: String)
}
