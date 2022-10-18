package org.cornfarmer.persentation.mycomment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.databinding.ItemMyCommentBinding
import org.cornfarmer.data.model.response.MyCommentResult

class MyCommentRVAdapter(private val reviewList: ArrayList<MyCommentResult>) :
    RecyclerView.Adapter<MyCommentRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(getMyCommentResult: MyCommentResult, position: Int)
    }

    interface MyModifyBtnClickListener {
        fun onModifyBtnClick(
            getMyCommentResult: MyCommentResult,
            position: Int,
            reviewInfo: org.cornfarmer.data.model.request.RequestModifyComment
        )
    }

    private lateinit var myItemClickListener: MyItemClickListener
    private lateinit var myModifyBtnClickListener: MyModifyBtnClickListener

    fun setMyItemClickLisetenr(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    fun setMyModifyBtnClickListener(itemClickListener: MyModifyBtnClickListener) {
        myModifyBtnClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemMyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position], position)
        holder.binding.itemMycommentMovieImgIv.setOnClickListener {
            myItemClickListener.onItemClick(reviewList[position], position)
        }
        holder.binding.itemMycommentModifyBtnTv.setOnClickListener {
            myModifyBtnClickListener.onModifyBtnClick(
                reviewList[position],
                position,
                org.cornfarmer.data.model.request.RequestModifyComment(
                    reviewList[position].content,
                    reviewList[position].rate.toDouble()
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    inner class ViewHolder(val binding: ItemMyCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewList: MyCommentResult, position: Int) {
            binding.itemMycommentMovieTitleTv.text = reviewList.movie.movieTitle
            binding.itemMycommentReviewTv.text = reviewList.content
            Glide.with(itemView).load(reviewList.movie.moviePhoto.toString())
                .into(binding.itemMycommentMovieImgIv)
            if (reviewList.rate <= 0.1) {
                binding.itemMycommentRate1Iv.visibility = View.GONE
                binding.itemMycommentRate2Iv.visibility = View.GONE
                binding.itemMycommentRate3Iv.visibility = View.GONE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE

                binding.itemCommentRate1Iv.visibility = View.VISIBLE
                binding.itemCommentRate2Iv.visibility = View.VISIBLE
                binding.itemCommentRate3Iv.visibility = View.VISIBLE
                binding.itemCommentRate4Iv.visibility = View.VISIBLE
                binding.itemCommentRate5Iv.visibility = View.VISIBLE
            } else if (reviewList.rate <= 1.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.GONE
                binding.itemMycommentRate3Iv.visibility = View.GONE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE

                binding.itemCommentRate1Iv.visibility = View.GONE
                binding.itemCommentRate2Iv.visibility = View.VISIBLE
                binding.itemCommentRate3Iv.visibility = View.VISIBLE
                binding.itemCommentRate4Iv.visibility = View.VISIBLE
                binding.itemCommentRate5Iv.visibility = View.VISIBLE
            } else if (reviewList.rate <= 2.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.GONE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE

                binding.itemCommentRate1Iv.visibility = View.GONE
                binding.itemCommentRate2Iv.visibility = View.GONE
                binding.itemCommentRate3Iv.visibility = View.VISIBLE
                binding.itemCommentRate4Iv.visibility = View.VISIBLE
                binding.itemCommentRate5Iv.visibility = View.VISIBLE
            } else if (reviewList.rate <= 3.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.VISIBLE
                binding.itemMycommentRate4Iv.visibility = View.GONE
                binding.itemMycommentRate5Iv.visibility = View.GONE

                binding.itemCommentRate1Iv.visibility = View.GONE
                binding.itemCommentRate2Iv.visibility = View.GONE
                binding.itemCommentRate3Iv.visibility = View.GONE
                binding.itemCommentRate4Iv.visibility = View.VISIBLE
                binding.itemCommentRate5Iv.visibility = View.VISIBLE
            } else if (reviewList.rate <= 4.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.VISIBLE
                binding.itemMycommentRate4Iv.visibility = View.VISIBLE
                binding.itemMycommentRate5Iv.visibility = View.GONE

                binding.itemCommentRate1Iv.visibility = View.GONE
                binding.itemCommentRate2Iv.visibility = View.GONE
                binding.itemCommentRate3Iv.visibility = View.GONE
                binding.itemCommentRate4Iv.visibility = View.GONE
                binding.itemCommentRate5Iv.visibility = View.VISIBLE
            } else if (reviewList.rate <= 5.1) {
                binding.itemMycommentRate1Iv.visibility = View.VISIBLE
                binding.itemMycommentRate2Iv.visibility = View.VISIBLE
                binding.itemMycommentRate3Iv.visibility = View.VISIBLE
                binding.itemMycommentRate4Iv.visibility = View.VISIBLE
                binding.itemMycommentRate5Iv.visibility = View.VISIBLE
                binding.itemCommentRate1Iv.visibility = View.GONE
                binding.itemCommentRate2Iv.visibility = View.GONE
                binding.itemCommentRate3Iv.visibility = View.GONE
                binding.itemCommentRate4Iv.visibility = View.GONE
                binding.itemCommentRate5Iv.visibility = View.GONE
            }
        }
    }
}
