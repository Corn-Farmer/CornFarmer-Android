package org.cornfarmer.presentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.cornfarmer.data.model.response.MovieDto
import org.cornfarmer.databinding.ItemOttsBinding

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
            binding.tvTitle.text = movie.movieName
            binding.itemOttGenre.text =
                movie.movieGenreList.toString().replace("[", "").replace("]", "")
            Glide.with(itemView)
                .load(movie.moviePhotoList[0])
                .centerCrop()
                .into(binding.ivImage)
            binding.tvCount.text = movie.likeCnt.toString()
            if (movie.liked == true) {
                binding.ivLike.visibility = View.GONE
                binding.ivLikeColor.visibility = View.VISIBLE
            } else {
                binding.ivLike.visibility = View.VISIBLE
                binding.ivLikeColor.visibility = View.GONE
            }
        }
    }
}
