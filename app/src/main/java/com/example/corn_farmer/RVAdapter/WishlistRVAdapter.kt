package com.example.cornfarmer_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.corn_farmer.databinding.ItemWishmovieBinding

class WishlistRVAdapter(private val wishList : ArrayList<wishMovie>) : RecyclerView.Adapter<WishlistRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : WishlistRVAdapter.ViewHolder {
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
        fun bind(wishMovie : wishMovie) {
            binding.wishlistMovie1ImageIv.setImageResource(wishMovie.movie_image)
            binding.wishlistMovie1TextTv.text = wishMovie.title
        }
    }
}