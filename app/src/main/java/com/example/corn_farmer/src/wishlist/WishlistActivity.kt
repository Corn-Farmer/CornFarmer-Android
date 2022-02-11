package com.example.corn_farmer.src.wishlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.corn_farmer.src.wishlist.model.getWishMovie
import com.example.corn_farmer.src.wishlist.model.getWishMovieResult
import com.example.cornfarmer_android.R
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

        if(servertoken==""){
            Toast.makeText(this, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
        }
        else{
            var service = WishlistService(this, userIdx!!, servertoken!!)
            service.tryGetWishlist()
        }


        binding.wishlistPreviousBtn1Iv.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onGetWishlistSuccess(response: getWishMovie) {
        val wishMovie = response.result
        val wishlistRVAdapter = WishlistRVAdapter(wishMovie)
        binding.wishlistRV.adapter = wishlistRVAdapter
        binding.wishlistRV.layoutManager = GridLayoutManager(
            this, 3
        )
        wishlistRVAdapter.setMyItemClickListener(object : WishlistRVAdapter.MyItemClickListener{
            override fun onItemClick(wishMovie: getWishMovieResult, position: Int) {
                val movieIdx = wishMovie.movieIdx
                val intent = Intent(this@WishlistActivity, MainActivity::class.java)
                intent.putExtra("movieIdx", movieIdx)
                startActivity(intent)
            }


        })
    }

    override fun onGetWishlistFailure(message: String) {
        Toast.makeText(this, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}