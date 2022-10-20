package org.cornfarmer.presentation.detail.adapter

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
import org.cornfarmer.presentation.detail.DeclarationActivity

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

        holder.binding.ivSetting.setOnClickListener {
            val context = holder.itemView.context

            val mDialogView =
                LayoutInflater.from(context)
                    .inflate(R.layout.detail_declation_dialog, null)
            val mBuilder = AlertDialog.Builder(context, R.style.SelectDeclation)
                .setView(mDialogView)

            val alertDialog = mBuilder.show()

            alertDialog.findViewById<ImageView>(R.id.iv_declaration)
                ?.setOnClickListener {
                    val intent = Intent(context, DeclarationActivity::class.java)
                    intent.putExtra("reviewIdx", reviewList[position].reviewIdx)
                    context.startActivity(intent)

                    alertDialog.dismiss()
                }
        }

        holder.binding.ivLikeColor.setOnClickListener {
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position, token)
            if (token != "") {
                likeCnt -= 1
                holder.binding.tvCount.text = "$likeCnt"
                holder.binding.ivLikeColor.visibility = View.GONE
                holder.binding.ivLikeNoColorSecond.visibility = View.VISIBLE
            }
        }
        holder.binding.ivLikeNoColorSecond.setOnClickListener {
            commentLikeBtnClickListener.onHeartClick(reviewList[position], position, token)
            if (token != "") {
                likeCnt += 1
                holder.binding.tvCount.text = "$likeCnt"
                holder.binding.ivLikeColor.visibility = View.VISIBLE
                holder.binding.ivLikeNoColorSecond.visibility = View.GONE
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
            binding.tvContent.text = reviewList.contents
            binding.tvDate.text = createAt
            binding.tvCount.text = reviewList.likeCnt.toString()

            binding.tvNickname.text = reviewList.writer.writerNickname

            Glide.with(itemView).load(reviewList.writer.writerPhoto)
                .into(binding.ivImage)

            if (reviewList.liked) {
                binding.ivLikeColor.visibility = View.VISIBLE
                binding.ivLikeNoColorSecond.visibility = View.GONE
            } else {
                binding.ivLikeColor.visibility = View.GONE
                binding.ivLikeNoColorSecond.visibility = View.VISIBLE
            }
        }
    }
}
