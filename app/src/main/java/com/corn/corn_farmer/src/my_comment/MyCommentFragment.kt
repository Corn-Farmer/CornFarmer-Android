package com.corn.corn_farmer.src.my_comment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentMyCommentBinding

class MyCommentFragment(val nickname : String) : Fragment(), MyCommentFragmentView {
    lateinit var binding : FragmentMyCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCommentBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val userIdx = sharedPreferences?.getInt("userIdx", -1000)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        val nickname = sharedPreferences?.getString("nickname",null)
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
        },200)
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
        if(result.toString()=="[]"){
            Toast.makeText(context,"후기를 작성해 주세요!",Toast.LENGTH_SHORT).show()
        }
        else{
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
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, DetailFragment(getMyCommentResult.movie.movieIdx, -1, ""))
                    .commitAllowingStateLoss()
            }
        })
        MyCommentRVAdapter.setMyModifyBtnClickListener(object : MyCommentRVAdapter.MyModifyBtnClickListener {
            override fun onModifyBtnClick(getMyCommentResult: getMyCommentResult, position: Int, reviewInfo : sendModifyComment) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, MyCommentModifyFragment(reviewInfo, getMyCommentResult.reviewIdx, nickname))
                    .commitAllowingStateLoss()
            }
        })
    }

    override fun onGetMyCommentFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    fun sortReview() {
        binding.mycommentSortLikeTv.setOnClickListener { //좋아요순
            val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.mycommentSortRecentTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortRateTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortLikeTv.setTextColor(Color.BLACK)
            val service = MyCommentService(this, userIdx!!, "like", servertoken!!)
            service.tryGetMyComment()
        }
        binding.mycommentSortRateTv.setOnClickListener { //후기많은순
            val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.mycommentSortRecentTv.setTextColor(Color.LTGRAY)
            binding.mycommentSortRateTv.setTextColor(Color.BLACK)
            binding.mycommentSortLikeTv.setTextColor(Color.LTGRAY)
            val service = MyCommentService(this, userIdx!!, "rate", servertoken!!)
            service.tryGetMyComment()
        }
        binding.mycommentSortRecentTv.setOnClickListener { // 최신순
            val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
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