package com.example.cornfarmer_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.corn_farmer.databinding.ItemCommentBinding

class CommentRVAdapter(private val commentList : ArrayList<comment>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRVAdapter.ViewHolder {
        val binding : ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentRVAdapter.ViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
       return commentList.size
    }

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment : comment) {
            binding.detailCommentUserNicknameTv.text = comment.nickname
            binding.detailCommentTextTv.text = comment.comment
        }
    }
}