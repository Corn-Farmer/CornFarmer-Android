package com.corn.corn_farmer.src.my_comment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.src.detail.DetailFragment
import com.corn.corn_farmer.src.loading.CustomLoadingDialog
import com.corn.corn_farmer.src.my_comment.model.getMyComment
import com.corn.corn_farmer.src.my_comment.model.getMyCommentResult
import com.corn.corn_farmer.src.my_comment_modify.MyCommentModifyFragment
import com.corn.corn_farmer.src.my_comment_modify.model.sendModifyComment
import com.corn.corn_farmer.src.profile.ProfileFragment
import com.corn.corn_farmer.util.ext.showToast
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentMyCommentBinding

class MyCommentFragment(val nickname: String) : Fragment(), MyCommentFragmentView {
    private lateinit var binding: FragmentMyCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_comment, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val userIdx = sharedPreferences?.getInt("userIdx", -1000)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        val nickname = sharedPreferences?.getString("nickname", null)
        binding.mycommentSubtitleNicknameTv.text = nickname
        binding.mycommentSortRecentTv.setTextColor(Color.BLACK)
        binding.mycommentSortRateTv.setTextColor(Color.LTGRAY)
        binding.mycommentSortLikeTv.setTextColor(Color.LTGRAY)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        }, 200)
        val service = MyCommentService(this, userIdx!!, "rate", servertoken!!)
        service.tryGetMyComment()
        sortReview()
        binding.mycommentBackBtnIv.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.callFragment(ProfileFragment())
        }

        return binding.root
    }

    override fun onGetMyCommentSuccess(response: getMyComment) {
        val result = response!!.result!!
        if (result.toString() == "[]") {
            activity?.showToast("후기를 작성해 주세요!")
        } else {
            binding.mycommentSubtitleNicknameTv.text = result[0].nickname
        }

        val MyCommentRVAdapter = MyCommentRVAdapter(result)
        binding.mycommentRV.adapter = MyCommentRVAdapter
        binding.mycommentRV.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        MyCommentRVAdapter.setMyItemClickLisetenr(object : MyCommentRVAdapter.MyItemClickListener {
            override fun onItemClick(getMyCommentResult: getMyCommentResult, position: Int) {
                val mActivity = activity as MainActivity // 메인 액티비티
                mActivity.callFragment(DetailFragment(getMyCommentResult.movie.movieIdx, -1, ""))
            }
        })
        MyCommentRVAdapter.setMyModifyBtnClickListener(object :
                MyCommentRVAdapter.MyModifyBtnClickListener {
                override fun onModifyBtnClick(
                    getMyCommentResult: getMyCommentResult,
                    position: Int,
                    reviewInfo: sendModifyComment
                ) {
                    val mActivity = activity as MainActivity // 메인 액티비티
                    mActivity.callFragment(
                        MyCommentModifyFragment(
                            reviewInfo,
                            getMyCommentResult.reviewIdx,
                            nickname
                        )
                    )
                }
            })
    }

    override fun onGetMyCommentFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
    }

    fun sortReview() {
        binding.mycommentSortLikeTv.setOnClickListener { // 좋아요순
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.mycommentSortRecentTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortRateTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortLikeTv.setTextColor(Color.BLACK)
            val service = MyCommentService(this, userIdx!!, "like", servertoken!!)
            service.tryGetMyComment()
        }
        binding.mycommentSortRateTv.setOnClickListener { // 후기많은순
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.mycommentSortRecentTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortRateTv.setTextColor(Color.BLACK)
            binding.mycommentSortLikeTv.setTextColor(Color.LTGRAY)
            val service = MyCommentService(this, userIdx!!, "rate", servertoken!!)
            service.tryGetMyComment()
        }
        binding.mycommentSortRecentTv.setOnClickListener { // 최신순
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.mycommentSortRecentTv.setTextColor(Color.BLACK)
            binding.mycommentSortRateTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortLikeTv.setTextColor(Color.LTGRAY)
            val service = MyCommentService(this, userIdx!!, "recent", servertoken!!)
            service.tryGetMyComment()
        }
    }
}
