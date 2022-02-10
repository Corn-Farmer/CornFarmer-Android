package com.example.corn_farmer.src.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.wishlist.model.getWishMovieResult
import com.example.cornfarmer_android.databinding.ItemWishmovieBinding

class WishlistRVAdapter(private val wishList : ArrayList<getWishMovieResult>) : RecyclerView.Adapter<WishlistRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val binding : ItemWishmovieBinding = ItemWishmovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wishList[position])
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    inner class ViewHolder(val binding : ItemWishmovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wishMovie : getWishMovieResult) {
            Glide.with(itemView).load(wishMovie.moviePhoto).into(binding.wishlistMovie1ImageIv)
            binding.wishlistMovie1TextTv.text = wishMovie.movieTitle
        }
    }
}