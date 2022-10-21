package org.cornfarmer.presentation.recommend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.cornfarmer.data.model.response.MovieInfo
import org.cornfarmer.databinding.ItemOttsBinding

class OttRVAdapter(private val movieInfoList: ArrayList<MovieInfo>) :
    RecyclerView.Adapter<OttRVAdapter.Viewholder>() {

    interface MyItemClickListener {
        fun onItemClick(movieInfo: MovieInfo, position: Int)
    }

    interface HeartClickListener {
        fun onHeartClick(movieInfo: MovieInfo, position: Int)
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
        holder.binding.cvImage.setOnClickListener {
            myItemClickListener.onItemClick(movieInfoList[position], position)
            holder.bind(movieInfoList[position])
            Log.d("heart", "item")
        }
    }

    override fun getItemCount(): Int {
        return movieInfoList.size
    }

    inner class Viewholder(val binding: ItemOttsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieInfo: MovieInfo) {
            Glide.with(itemView).load(movieInfo.moviePhotoList[0]).into(binding.ivImage)
            binding.tvTitle.text = movieInfo.movieName
            binding.itemOttGenre.text = movieInfo.movieGenreList.joinToString(separator = ",")
            binding.tvCount.text = movieInfo.likeCnt.toString()
            if (movieInfo.liked) {
                binding.ivLike.visibility = View.GONE
                binding.ivLikeColor.visibility = View.VISIBLE
            } else {
                binding.ivLike.visibility = View.VISIBLE
                binding.ivLikeColor.visibility = View.GONE
            }
        }
    }
}
