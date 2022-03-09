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

class CommentRVAdapter(private val reviewList : ArrayList<getReviewList>, val token : String) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    interface CommentLikeBtnClickListener{
        fun onHeartClick(getReviewList: getReviewList, position: Int, token : String)
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

        holder.binding.detailCommentDeclarationIv.setOnClickListener {

//            val mDialogView =
//                LayoutInflater.from(this).inflate(R.layout.image_select_custom_dialog, null)
//            val mBuilder = AlertDialog.Builder(this, R.style.SelectAlertDialog)
//                .setView(mDialogView)
//
//            val alertDialog = mBuilder.show()
//            alertDialog.window?.setGravity(Gravity.BOTTOM)
//
//            alertDialog.findViewById<Button>(R.id.select_camera_bt)?.setOnClickListener {
//                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                takePictureIntent.resolveActivity(packageManager)
//                startActivityForResult(takePictureIntent, 100)
//                alertDialog.dismiss()
//            }
//
//            alertDialog.findViewById<Button>(R.id.select_album_bt)?.setOnClickListener {
//                val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//                startActivityForResult(gallery, 200)
//                alertDialog.dismiss()
//            }
//
//            alertDialog.findViewById<ImageView>(R.id.select_cancel_bt)?.setOnClickListener {
//                alertDialog.dismiss()
//            }

//            val intent = Intent(holder.itemView?.context, MainActivity::class.java)
//            ContextCompat.startActivity(holder.itemView.context, intent, null)

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

    inner class ViewHolder(val binding : ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList : getReviewList, position: Int) {
            var createAt = reviewList.createAt.substring(0,10)
            binding.detailCommentUserNicknameTv.text = reviewList.writer.writerNickname
            binding.detailCommentTextTv.text = reviewList.contents
            binding.detailCommentDateTv.text = createAt
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