package org.cornfarmer.persentation.wishlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityWishlistBinding
import org.cornfarmer.data.model.response.ResponseWishMovie
import org.cornfarmer.data.model.response.WishMovieResult
import org.cornfarmer.data.repository.WishlistService
import org.cornfarmer.data.view.WishlistView
import org.cornfarmer.persentation.main.MainActivity
import org.cornfarmer.persentation.wishlist.adapter.WishlistRVAdapter
import org.cornfarmer.util.ext.showToast

class WishlistActivity : AppCompatActivity(), WishlistView {

    private lateinit var binding: ActivityWishlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist)

        val sharedPreferences = getSharedPreferences("join", Context.MODE_PRIVATE)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        val userIdx = sharedPreferences?.getInt("userIdx", 0)

        if (servertoken == "") {
            showToast("로그인이 필요한 서비스입니다.")
        } else {
            val service = WishlistService(this, userIdx!!, servertoken!!)
            service.tryGetWishlist()
        }

        binding.wishlistPreviousBtn1Iv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onGetWishlistSuccess(response: ResponseWishMovie) {
        val wishMovie = response.result
        val wishlistRVAdapter = WishlistRVAdapter(wishMovie)
        binding.wishlistRV.adapter = wishlistRVAdapter
        binding.wishlistRV.layoutManager = GridLayoutManager(
            this,
            3
        )
        wishlistRVAdapter.setMyItemClickListener(object : WishlistRVAdapter.MyItemClickListener {
            override fun onItemClick(wishMovie: WishMovieResult, position: Int) {
                val movieIdx = wishMovie.movieIdx
                val intent = Intent(this@WishlistActivity, MainActivity::class.java)
                intent.putExtra("movieIdx", movieIdx)
                startActivity(intent)
            }
        })
    }

    override fun onGetWishlistFailure(message: String) {
        showToast("네트워크 연결에 실패했습니다.")
    }
}
