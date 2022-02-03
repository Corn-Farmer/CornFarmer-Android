package com.example.corn_farmer.src.recommend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.recommend.model.movieInfo
import com.example.cornfarmer_android.databinding.ItemOttsBinding

class OttRVAdapter(private val movieInfoList : ArrayList<movieInfo>) : RecyclerView.Adapter<OttRVAdapter.Viewholder>(){

    interface MyItemClickListener{
        fun onItemClick(movieInfo: movieInfo, position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemOttsBinding = ItemOttsBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(movieInfoList[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(movieInfoList[position], position)
            holder.bind(movieInfoList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieInfoList.size
    }


    inner class Viewholder(val binding : ItemOttsBinding ) : RecyclerView.ViewHolder(binding.root){

        fun bind(movieInfo: movieInfo){
            Glide.with(itemView).load(movieInfo!!.moviePhotoList[0]).into(binding.itemOttImgIv)
            binding.itemOttTitle.text = movieInfo.movieName
            binding.itemOttGenre.text = movieInfo.movieGenreList?.joinToString(separator = ",")
            if(!movieInfo.isLiked){
                binding.itemOttLikeIv.visibility = View.VISIBLE
                binding.itemOttLikeFillImgIv.visibility = View.INVISIBLE
            }
            else{
                binding.itemOttLikeIv.visibility = View.INVISIBLE
                binding.itemOttLikeFillImgIv.visibility = View.VISIBLE
            }
            binding.itemOttLikeCountTv.text = ""
        }
    }
}