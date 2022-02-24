package com.corn.corn_farmer.src.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.corn_farmer.src.home.model.MovieDto
import com.corn.cornfarmer_android.databinding.ItemOttsBinding

class TodayMovieRVAdapter(private val movieList : List<MovieDto>) : RecyclerView.Adapter<TodayMovieRVAdapter.Viewholder>() {

    interface MyItemClickListener{
        fun onItemClick(MovieDto: MovieDto, position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemOttsBinding = ItemOttsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        Log.d("test","test")
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(movieList[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(movieList[position], position)
        }
    }

    override fun getItemCount(): Int = movieList.size

    inner class Viewholder(private val binding : ItemOttsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie : MovieDto){
            binding.itemOttTitle.text = movie.movieName
            binding.itemOttGenre.text = movie.movieGenreList.toString().replace("[","").replace("]","")
            Glide.with(itemView)
                .load(movie.moviePhotoList[0])
                .centerCrop()
                .into(binding.itemOttImgIv)
            binding.itemOttLikeCountTv.text = movie.likeCnt.toString()
            if(movie.liked == true){
                binding.itemOttLikeFillImgIv.visibility = View.VISIBLE
                binding.itemOttLikeIv.visibility = View.GONE
            }
            else{
                binding.itemOttLikeFillImgIv.visibility = View.GONE
                binding.itemOttLikeIv.visibility = View.VISIBLE
            }

        }
    }
}