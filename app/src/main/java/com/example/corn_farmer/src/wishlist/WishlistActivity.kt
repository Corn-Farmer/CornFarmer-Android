package com.example.corn_farmer.src.wishlist

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.corn_farmer.src.wishlist.model.getWishMovie
import com.example.cornfarmer_android.databinding.ActivityWishlistBinding


class WishlistActivity: AppCompatActivity(), WishlistView {
    lateinit var binding : ActivityWishlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("join", Context.MODE_PRIVATE)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        val userIdx = sharedPreferences?.getInt("userIdx", 0)

        var service = WishlistService(this, userIdx!!, servertoken!!)
        service.tryGetWishlist()
    }

    override fun onGetWishlistSuccess(response: getWishMovie) {
        val wishMovie = response.result
        val wishlistRVAdapter = WishlistRVAdapter(wishMovie)
        binding.wishlistRV.adapter = wishlistRVAdapter
        binding.wishlistRV.layoutManager = GridLayoutManager(
            this, 3
        )
    }

    override fun onGetWishlistFailure(message: String) {
        Toast.makeText(this, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}