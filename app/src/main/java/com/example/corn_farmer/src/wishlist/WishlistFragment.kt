package com.example.corn_farmer.src.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentWishlistBinding
import com.example.corn_farmer.src.wishlist.model.wishMovie

class WishlistFragment: Fragment() {
    lateinit var binding : FragmentWishlistBinding
    private var wishMovieList = ArrayList<wishMovie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)

        wishMovieList.apply{
            add(wishMovie(R.drawable.ic_disney_plus, "disney"))
            add(wishMovie(R.drawable.ic_disney_plus, "whatif"))
            add(wishMovie(R.drawable.ic_disney_plus, "frozen"))
            add(wishMovie(R.drawable.ic_disney_plus, "snow"))
            add(wishMovie(R.drawable.ic_disney_plus, "runningman"))
            add(wishMovie(R.drawable.ic_disney_plus, "myname"))
        }

        val RVadapter = WishlistRVAdapter(wishMovieList)
        binding.wishlistRV.adapter = RVadapter
        binding.wishlistRV.layoutManager = GridLayoutManager(
            context, 3
        )
        return binding.root
    }
}