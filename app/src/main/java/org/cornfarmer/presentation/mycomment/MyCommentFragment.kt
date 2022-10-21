package org.cornfarmer.presentation.mycomment

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
import org.cornfarmer.R
import org.cornfarmer.data.model.response.MyCommentResult
import org.cornfarmer.data.model.response.ResponseMyComment
import org.cornfarmer.data.repository.MyCommentService
import org.cornfarmer.data.view.MyCommentFragmentView
import org.cornfarmer.databinding.FragmentMyCommentBinding
import org.cornfarmer.presentation.commentmodify.MyCommentModifyFragment
import org.cornfarmer.presentation.detail.DetailFragment
import org.cornfarmer.presentation.loading.CustomLoadingDialog
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.profile.ProfileFragment
import org.cornfarmer.util.ext.showToast

class MyCommentFragment(val nickname: String) : Fragment(), MyCommentFragmentView {
    private lateinit var binding: FragmentMyCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_comment, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val userIdx = sharedPreferences?.getInt("userIdx", -1000)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        val nickname = sharedPreferences?.getString("nickname", null)
        binding.tvNickname.text = nickname
        binding.tvRecent.setTextColor(Color.BLACK)
        binding.tvSortRate.setTextColor(Color.LTGRAY)
        binding.tvSortLike.setTextColor(Color.LTGRAY)
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
        binding.ivBack.setOnClickListener {
            val mActivity = activity as MainActivity
            mActivity.callFragment(ProfileFragment())
        }

        return binding.root
    }

    override fun onGetMyCommentSuccess(response: ResponseMyComment) {
        val result = response.result
        if (result.toString() == "[]") {
            activity?.showToast("후기를 작성해 주세요!")
        } else {
            binding.tvNickname.text = result[0].nickname
        }

        val MyCommentRVAdapter = MyCommentRVAdapter(result)
        binding.rcvMycomment.adapter = MyCommentRVAdapter
        binding.rcvMycomment.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        MyCommentRVAdapter.setMyItemClickLisetenr(object : MyCommentRVAdapter.MyItemClickListener {
            override fun onItemClick(getMyCommentResult: MyCommentResult, position: Int) {
                val mActivity = activity as MainActivity // 메인 액티비티
                mActivity.callFragment(DetailFragment(getMyCommentResult.movie.movieIdx, -1, ""))
            }
        })
        MyCommentRVAdapter.setMyModifyBtnClickListener(object :
                MyCommentRVAdapter.MyModifyBtnClickListener {
                override fun onModifyBtnClick(
                    getMyCommentResult: MyCommentResult,
                    position: Int,
                    reviewInfo: org.cornfarmer.data.model.request.RequestModifyComment
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

    private fun sortReview() {
        binding.tvSortLike.setOnClickListener {
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.tvRecent.setTextColor(Color.LTGRAY)
            binding.tvSortRate.setTextColor(Color.LTGRAY)
            binding.tvSortLike.setTextColor(Color.BLACK)
            val service = MyCommentService(this, userIdx!!, "like", servertoken!!)
            service.tryGetMyComment()
        }
        binding.tvSortRate.setOnClickListener {
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.tvRecent.setTextColor(Color.LTGRAY)
            binding.tvSortRate.setTextColor(Color.BLACK)
            binding.tvSortLike.setTextColor(Color.LTGRAY)
            val service = MyCommentService(this, userIdx!!, "rate", servertoken!!)
            service.tryGetMyComment()
        }
        binding.tvRecent.setOnClickListener {
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val userIdx = sharedPreferences?.getInt("userIdx", -1000)
            val servertoken = sharedPreferences?.getString("servertoken", "")
            binding.tvRecent.setTextColor(Color.BLACK)
            binding.tvSortRate.setTextColor(Color.LTGRAY)
            binding.tvSortLike.setTextColor(Color.LTGRAY)
            val service = MyCommentService(this, userIdx!!, "recent", servertoken!!)
            service.tryGetMyComment()
        }
    }
}
