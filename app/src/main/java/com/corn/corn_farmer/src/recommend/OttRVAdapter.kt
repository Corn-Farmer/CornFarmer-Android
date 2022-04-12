package com.corn.corn_farmer.src.recommend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.corn_farmer.src.recommend.model.movieInfo
import com.corn.cornfarmer_android.databinding.ItemOttsBinding

class OttRVAdapter(private val movieInfoList : ArrayList<movieInfo>) : RecyclerView.Adapter<OttRVAdapter.Viewholder>() {

    interface MyItemClickListener {
        fun onItemClick(movieInfo: movieInfo, position: Int)
    }

    interface HeartClickListener {
        fun onHeartClick(movieInfo: movieInfo, position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener
    private lateinit var myHeartClickListener: HeartClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    fun setHeartClickListener(heartClickListener: HeartClickListener) {
        myHeartClickListener = heartClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding: ItemOttsBinding =
            ItemOttsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(movieInfoList[position])
        holder.binding.itemOttImgCv.setOnClickListener {
            myItemClickListener.onItemClick(movieInfoList[position], position)
            holder.bind(movieInfoList[position])
            Log.d("heart", "item")
        }
    }

    override fun getItemCount(): Int {
        return movieInfoList.size
    }


    inner class Viewholder(val binding: ItemOttsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieInfo: movieInfo) {
            Glide.with(itemView).load(movieInfo!!.moviePhotoList[0]).into(binding.itemOttImgIv)
            binding.itemOttTitle.text = movieInfo.movieName
            binding.itemOttGenre.text = movieInfo.movieGenreList?.joinToString(separator = ",")
            binding.itemOttLikeCountTv.text = movieInfo.likeCnt.toString()
            if (movieInfo.liked == true) {
                binding.itemOttLikeIv.visibility = View.GONE
                binding.itemOttLikeFillImgIv.visibility = View.VISIBLE
            } else {
                binding.itemOttLikeIv.visibility = View.VISIBLE
                binding.itemOttLikeFillImgIv.visibility = View.GONE
            }
        }
    }
}