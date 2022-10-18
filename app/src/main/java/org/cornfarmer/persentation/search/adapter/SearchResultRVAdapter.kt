package org.cornfarmer.persentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.databinding.ItemOttsBinding
import org.cornfarmer.data.model.response.MovieDto

class SearchResultRVAdapter(private val movieList: List<MovieDto>) :
    RecyclerView.Adapter<SearchResultRVAdapter.Viewholder>() {

    interface MyItemClickListener {
        fun onItemClick(movieList: MovieDto, position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding: ItemOttsBinding =
            ItemOttsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(movieList[position])
        holder.itemView.setOnClickListener {
            myItemClickListener.onItemClick(movieList[position], position)
        }
    }

    override fun getItemCount(): Int = movieList.size

    inner class Viewholder(private val binding: ItemOttsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDto) {
            binding.itemOttTitle.text = movie.movieName
            binding.itemOttGenre.text =
                movie.movieGenreList.toString().replace("[", "").replace("]", "")
            Glide.with(itemView)
                .load(movie.moviePhotoList[0])
                .centerCrop()
                .into(binding.itemOttImgIv)
            binding.itemOttLikeCountTv.text = movie.likeCnt.toString()
            if (movie.liked == true) {
                binding.itemOttLikeIv.visibility = View.GONE
                binding.itemOttLikeFillImgIv.visibility = View.VISIBLE
            } else {
                binding.itemOttLikeIv.visibility = View.VISIBLE
                binding.itemOttLikeFillImgIv.visibility = View.GONE
            }
        }
    }
}
