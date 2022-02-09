package com.example.corn_farmer.src.my_comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.my_comment.model.getMyCommentResult
import com.example.cornfarmer_android.databinding.ItemMyCommentBinding

class MyCommentRVAdapter(private val reviewList : ArrayList<getMyCommentResult>) : RecyclerView.Adapter<MyCommentRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCommentRVAdapter.ViewHolder {
        val binding = ItemMyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCommentRVAdapter.ViewHolder, position: Int) {
        holder.bind(reviewList[position], position)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    inner class ViewHolder(val binding : ItemMyCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList : getMyCommentResult, position: Int) {
            binding.itemMycommentMovieTitleTv.text = reviewList.movie.movieTitle
            binding.itemMycommentReviewTv.text = reviewList.content
            Glide.with(itemView).load(reviewList.movie.moviePhoto.toString()).into(binding.itemMycommentMovieImgIv)
            if (reviewList.rate <= 0.1) {
                binding.itemMycommentRate1Iv.visibility = View.INVISIBLE
                binding.itemMycommentRate2Iv.visibility = View.GONE
                binding.itemMycommentRate3Iv.visibility = View.GONE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE
            } else if (reviewList.rate <= 1.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.GONE
                binding.itemMycommentRate3Iv.visibility = View.GONE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE
            }else if (reviewList.rate <= 2.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.GONE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE
            } else if (reviewList.rate <= 3.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.VISIBLE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE
            } else if (reviewList.rate <= 4.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.VISIBLE
                binding.itemMycommentRate4Iv.visibility = View.VISIBLE
                binding.itemMycommentRate5Iv.visibility = View.GONE
            } else if (reviewList.rate <= 5.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.VISIBLE
                binding.itemMycommentRate4Iv.visibility = View.VISIBLE
                binding.itemMycommentRate5Iv.visibility = View.VISIBLE
            }
        }
    }
}