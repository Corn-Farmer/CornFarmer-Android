package com.corn.corn_farmer.src.wishlist

import com.corn.corn_farmer.src.wishlist.model.getWishMovie

interface WishlistView {
    fun onGetWishlistSuccess(response: getWishMovie)
    fun onGetWishlistFailure(message: String)
}