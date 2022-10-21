package org.cornfarmer.presentation.mycomment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.cornfarmer.data.model.response.MyCommentResult
import org.cornfarmer.databinding.ItemMyCommentBinding

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
        holder.binding.ivImage.setOnClickListener {
            myItemClickListener.onItemClick(reviewList[position], position)
        }
        holder.binding.tvModify.setOnClickListener {
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
            binding.tvTitle.text = reviewList.movie.movieTitle
            binding.tvReview.text = reviewList.content
            Glide.with(itemView).load(reviewList.movie.moviePhoto)
                .into(binding.ivImage)
            if (reviewList.rate <= 0.1) {
                binding.ivRateOneColor.visibility = View.GONE
                binding.ivRateTwoColor.visibility = View.GONE
                binding.ivRateThreeColor.visibility = View.GONE
                binding.ivRateFourColor.visibility = View.GONE
                binding.ivRateFiveColor.visibility = View.GONE

                binding.ivRateOne.visibility = View.VISIBLE
                binding.ivRateTwo.visibility = View.VISIBLE
                binding.ivRateThree.visibility = View.VISIBLE
                binding.ivRateFour.visibility = View.VISIBLE
                binding.ivRateFive.visibility = View.VISIBLE
            } else if (reviewList.rate <= 1.1) {
                binding.ivRateOneColor.visibility = View.VISIBLE
                binding.ivRateTwoColor.visibility = View.GONE
                binding.ivRateThreeColor.visibility = View.GONE
                binding.ivRateFourColor.visibility = View.GONE
                binding.ivRateFiveColor.visibility = View.GONE

                binding.ivRateOne.visibility = View.GONE
                binding.ivRateTwo.visibility = View.VISIBLE
                binding.ivRateThree.visibility = View.VISIBLE
                binding.ivRateFour.visibility = View.VISIBLE
                binding.ivRateFive.visibility = View.VISIBLE
            } else if (reviewList.rate <= 2.1) {
                binding.ivRateOneColor.visibility = View.VISIBLE
                binding.ivRateTwoColor.visibility = View.VISIBLE
                binding.ivRateThreeColor.visibility = View.GONE
                binding.ivRateFourColor.visibility = View.GONE
                binding.ivRateFiveColor.visibility = View.GONE

                binding.ivRateOne.visibility = View.GONE
                binding.ivRateTwo.visibility = View.GONE
                binding.ivRateThree.visibility = View.VISIBLE
                binding.ivRateFour.visibility = View.VISIBLE
                binding.ivRateFive.visibility = View.VISIBLE
            } else if (reviewList.rate <= 3.1) {
                binding.ivRateOneColor.visibility = View.VISIBLE
                binding.ivRateTwoColor.visibility = View.VISIBLE
                binding.ivRateThreeColor.visibility = View.VISIBLE
                binding.ivRateFourColor.visibility = View.GONE
                binding.ivRateFiveColor.visibility = View.GONE

                binding.ivRateOne.visibility = View.GONE
                binding.ivRateTwo.visibility = View.GONE
                binding.ivRateThree.visibility = View.GONE
                binding.ivRateFour.visibility = View.VISIBLE
                binding.ivRateFive.visibility = View.VISIBLE
            } else if (reviewList.rate <= 4.1) {
                binding.ivRateOneColor.visibility = View.VISIBLE
                binding.ivRateTwoColor.visibility = View.VISIBLE
                binding.ivRateThreeColor.visibility = View.VISIBLE
                binding.ivRateFourColor.visibility = View.VISIBLE
                binding.ivRateFiveColor.visibility = View.GONE

                binding.ivRateOne.visibility = View.GONE
                binding.ivRateTwo.visibility = View.GONE
                binding.ivRateThree.visibility = View.GONE
                binding.ivRateFour.visibility = View.GONE
                binding.ivRateFive.visibility = View.VISIBLE
            } else if (reviewList.rate <= 5.1) {
                binding.ivRateOneColor.visibility = View.VISIBLE
                binding.ivRateTwoColor.visibility = View.VISIBLE
                binding.ivRateThreeColor.visibility = View.VISIBLE
                binding.ivRateFourColor.visibility = View.VISIBLE
                binding.ivRateFiveColor.visibility = View.VISIBLE

                binding.ivRateOne.visibility = View.GONE
                binding.ivRateTwo.visibility = View.GONE
                binding.ivRateThree.visibility = View.GONE
                binding.ivRateFour.visibility = View.GONE
                binding.ivRateFive.visibility = View.GONE
            }
        }
    }
}
