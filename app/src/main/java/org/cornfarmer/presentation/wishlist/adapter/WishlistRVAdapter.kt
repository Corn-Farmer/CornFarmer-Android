package org.cornfarmer.presentation.wishlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.cornfarmer.data.model.response.WishMovieResult
import org.cornfarmer.databinding.ItemWishmovieBinding

class WishlistRVAdapter(private val wishList: ArrayList<WishMovieResult>) :
    RecyclerView.Adapter<WishlistRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(wishMovie: WishMovieResult, position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWishmovieBinding =
            ItemWishmovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wishList[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(wishList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    inner class ViewHolder(val binding: ItemWishmovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wishMovie: WishMovieResult) {
            Glide.with(itemView).load(wishMovie.moviePhoto).into(binding.ivImage)
            binding.tvTitle.text = wishMovie.movieTitle
        }
    }
}
