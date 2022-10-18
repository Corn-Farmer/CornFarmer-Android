package org.cornfarmer.persentation.detail.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ItemCommentBinding
import org.cornfarmer.data.model.response.ReviewList
import org.cornfarmer.persentation.detail.DeclationActivity

class CommentRVAdapter(private val reviewList: ArrayList<ReviewList>, val token: String) :
    RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {

    interface CommentLikeBtnClickListener {
        fun onHeartClick(getReviewList: ReviewList, position: Int, token: String)
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
        holder.bind(reviewList[position])

        var likeCnt = reviewList[position].likeCnt

        holder.binding.detailCommentDeclarationIv.setOnClickListener {
            val context = holder.itemView.context

            val mDialogView =
                LayoutInflater.from(context)
                    .inflate(R.layout.detail_declation_dialog, null)
            val mBuilder = AlertDialog.Builder(context, R.style.SelectDeclation)
                .setView(mDialogView)

            val alertDialog = mBuilder.show()

            alertDialog.findViewById<ImageView>(R.id.detail_declation_button_iv)
                ?.setOnClickListener {
                    val intent = Intent(context, DeclationActivity::class.java)
                    intent.putExtra("reviewIdx", reviewList[position].reviewIdx)
                    context.startActivity(intent)

                    alertDialog.dismiss()
                }
        }

        holder.binding.detailComentHeartOnIv.setOnClickListener {
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position, token)
            if (token != "") {
                likeCnt -= 1
                holder.binding.detailCommentLikecntTv.text = "$likeCnt"
                holder.binding.detailComentHeartOnIv.visibility = View.GONE
                holder.binding.detailComentHeartOffIv.visibility = View.VISIBLE
            }
        }
        holder.binding.detailComentHeartOffIv.setOnClickListener {
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position, token)
            if (token != "") {
                likeCnt += 1
                holder.binding.detailCommentLikecntTv.text = "$likeCnt"
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

        fun bind(reviewList: ReviewList) {
            val createAt = reviewList.createAt.substring(0, 10)
            binding.detailCommentTextTv.text = reviewList.contents
            binding.detailCommentDateTv.text = createAt
            binding.detailCommentLikecntTv.text = reviewList.likeCnt.toString()

            binding.detailCommentUserNicknameTv.text = reviewList.writer.writerNickname

            Glide.with(itemView).load(reviewList.writer.writerPhoto)
                .into(binding.detailCommentUserProfileIv)

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
