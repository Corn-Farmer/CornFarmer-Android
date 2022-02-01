package com.example.cornfarmer_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corn_farmer.Item.getReviewAPI
import com.example.corn_farmer.Item.getReviewList
import com.example.cornfarmer_android.databinding.ItemCommentBinding

class CommentRVAdapter(private val reviewList : ArrayList<getReviewList>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRVAdapter.ViewHolder {
        val binding : ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentRVAdapter.ViewHolder, position: Int) {
        holder.bind(reviewList[position], position)
    }

    override fun getItemCount(): Int {
       return reviewList.size
    }

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList : getReviewList, position: Int) {
            binding.detailCommentUserNicknameTv.text = reviewList.writer.writerNickname
            binding.detailCommentTextTv.text = reviewList.contents
            binding.detailCommentDateTv.text = reviewList.createdAt
            binding.detailCommentLikecntTv.text = "+ ${reviewList.likeCnt.toString()}"
            Glide.with(itemView).load(reviewList.writer.writerPhoto.toString()).into(binding.detailCommentUserProfileIv)
        }
    }
}