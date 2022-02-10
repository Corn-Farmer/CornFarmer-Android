package com.example.corn_farmer.src.detail

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.detail.model.getReviewList
import com.example.cornfarmer_android.databinding.ItemCommentBinding
import kotlin.coroutines.coroutineContext

class CommentRVAdapter(private val reviewList : ArrayList<getReviewList>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    interface CommentLikeBtnClickListener{
        fun onHeartClick(getReviewList: getReviewList, position: Int)
    }

    private lateinit var commentLikeBtnClickListener : CommentLikeBtnClickListener

    fun setCommentLikeBtnClickListener(heartClickListener : CommentLikeBtnClickListener) {
        commentLikeBtnClickListener = heartClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemCommentBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position], position)
        var likeCnt = reviewList[position].likeCnt
        holder.binding.detailComentHeartOnIv.setOnClickListener {
            likeCnt = likeCnt - 1
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position)
            holder.binding.detailCommentLikecntTv.text = "${likeCnt}"
            holder.binding.detailComentHeartOnIv.visibility = View.GONE
            holder.binding.detailComentHeartOffIv.visibility = View.VISIBLE
        }
        holder.binding.detailComentHeartOffIv.setOnClickListener {
            likeCnt = likeCnt + 1
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position)
            holder.binding.detailCommentLikecntTv.text = "${likeCnt}"
            holder.binding.detailComentHeartOnIv.visibility = View.VISIBLE
            holder.binding.detailComentHeartOffIv.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
       return reviewList.size
    }

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList : getReviewList, position: Int) {
            binding.detailCommentUserNicknameTv.text = reviewList.writer.writerNickname
            binding.detailCommentTextTv.text = reviewList.contents
            binding.detailCommentDateTv.text = reviewList.createdAt
            binding.detailCommentLikecntTv.text = "${reviewList.likeCnt.toString()}"
            Glide.with(itemView).load(reviewList.writer.writerPhoto).into(binding.detailCommentUserProfileIv)
            Log.d("writerPhoto", reviewList.writer.writerPhoto)
            if (reviewList.liked) {
                binding.detailComentHeartOnIv.visibility = View.VISIBLE
                binding.detailComentHeartOffIv.visibility = View.GONE
            } else {
                binding.detailComentHeartOnIv.visibility = View.GONE
                binding.detailComentHeartOffIv.visibility = View.VISIBLE
            }
        }
    }
}