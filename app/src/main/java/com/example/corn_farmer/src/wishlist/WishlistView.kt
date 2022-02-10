package com.example.corn_farmer.src.wishlist

import com.example.corn_farmer.src.wishlist.model.getWishMovie

interface WishlistView {
    fun onGetWishlistSuccess(response: getWishMovie)
    fun onGetWishlistFailure(message: String)
}