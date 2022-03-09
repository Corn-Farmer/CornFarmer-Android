package com.corn.corn_farmer.src.detail

import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.src.detail.model.getReviewList
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ItemCommentBinding

class CommentRVAdapter(private val reviewList: ArrayList<getReviewList>, val token: String) :
    RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    interface CommentLikeBtnClickListener {
        fun onHeartClick(getReviewList: getReviewList, position: Int, token: String)
    }

    private lateinit var commentLikeBtnClickListener: CommentLikeBtnClickListener

    fun setCommentLikeBtnClickListener(heartClickListener: CommentLikeBtnClickListener) {
        commentLikeBtnClickListener = heartClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCommentBinding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position], position)
        var likeCnt = reviewList[position].likeCnt

        holder.binding.detailCommentDeclarationIv.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.detail_declation_dialog, null)
            val mBuilder = AlertDialog.Builder(holder.itemView.context, R.style.SelectDeclation)
                .setView(mDialogView)

            val alertDialog = mBuilder.show()
//            alertDialog.window?.setGravity(Gravity.BOTTOM)

            alertDialog.findViewById<ImageView>(R.id.detail_declation_button_iv)
                ?.setOnClickListener {

                    val intent = Intent(holder.itemView?.context, DeclationActivity::class.java)
                    intent.putExtra("reviewIdx", reviewList[position].reviewIdx)
                    ContextCompat.startActivity(holder.itemView.context, intent, null)

                    alertDialog.dismiss()
                }


        }

        holder.binding.detailComentHeartOnIv.setOnClickListener {
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position, token)
            if (token != "") {
                likeCnt = likeCnt - 1
                holder.binding.detailCommentLikecntTv.text = "${likeCnt}"
                holder.binding.detailComentHeartOnIv.visibility = View.GONE
                holder.binding.detailComentHeartOffIv.visibility = View.VISIBLE
            }
        }
        holder.binding.detailComentHeartOffIv.setOnClickListener {
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position, token)
            if (token != "") {
                likeCnt = likeCnt + 1
                holder.binding.detailCommentLikecntTv.text = "${likeCnt}"
                holder.binding.detailComentHeartOnIv.visibility = View.VISIBLE
                holder.binding.detailComentHeartOffIv.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    inner class ViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList: getReviewList, position: Int) {
            var createAt = reviewList.createAt.substring(0, 10)
            binding.detailCommentUserNicknameTv.text = reviewList.writer.writerNickname
            binding.detailCommentTextTv.text = reviewList.contents
            binding.detailCommentDateTv.text = createAt
            binding.detailCommentLikecntTv.text = "${reviewList.likeCnt.toString()}"
            Glide.with(itemView).load(reviewList.writer.writerPhoto)
                .into(binding.detailCommentUserProfileIv)
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