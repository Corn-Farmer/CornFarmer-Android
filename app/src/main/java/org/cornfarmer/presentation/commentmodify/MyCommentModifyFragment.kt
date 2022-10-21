package org.cornfarmer.presentation.commentmodify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.cornfarmer.R
import org.cornfarmer.data.model.request.RequestModifyComment
import org.cornfarmer.data.model.response.ResponseCommentModify
import org.cornfarmer.data.repository.MyCommentModifyService
import org.cornfarmer.data.view.MyCommentModifyFragmentView
import org.cornfarmer.databinding.FragmentMyCommentModifyBinding
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.mycomment.MyCommentFragment
import org.cornfarmer.util.ext.showToast

class MyCommentModifyFragment(
    private val reviewInfo: RequestModifyComment,
    val reviewIdx: Int,
    val nickname: String
) : Fragment(), MyCommentModifyFragmentView {
    private lateinit var binding: FragmentMyCommentModifyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_comment_modify, container, false)

        binding.etContent.setText(reviewInfo.content)

        setRate()
        getRate()

        binding.ivComplete.setOnClickListener {
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", null)
            val newReview = RequestModifyComment(
                binding.etContent.text.toString(),
                binding.tvSave.text.toString().toDouble()
            )
            val service = MyCommentModifyService(this, newReview, reviewIdx, servertoken!!)
            service.tryPutMyCommentModify()
        }

        binding.ivCancel.setOnClickListener {
            val mActivity = activity as MainActivity // 메인 액티비티
            mActivity.callFragment(MyCommentFragment(nickname))
        }

        return binding.root
    }

    override fun onPutMyCommentModifySuccess(response: ResponseCommentModify) {
        if (response.code == 2000) {
            activity?.showToast(response.message)
        } else {
            val mActivity = activity as MainActivity // 메인 액티비티
            mActivity.callFragment(MyCommentFragment(nickname))
            activity?.showToast("후기를 수정하였습니다.")
        }
    }

    override fun onPutMyCommentModifyFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
    }

    private fun setRate() {
        if (reviewInfo.rate <= 0.1) {
            binding.ivFirstCornColor.visibility = View.INVISIBLE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.VISIBLE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 1.1) {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 2.1) {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 3.1) {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 4.1) {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 5.1) {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.VISIBLE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.GONE
        }
    }

    private fun getRate() {
        binding.tvSave.text = reviewInfo.rate.toString()

        binding.ivFirstCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (1.0).toString()
        }

        binding.ivFirstCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.GONE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.VISIBLE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (0.0).toString()
        }

        binding.ivSecondCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (2.0).toString()
        }

        binding.ivSecondCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (1.0).toString()
        }

        binding.ivThirdCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (3.0).toString()
        }

        binding.ivThirdCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (2.0).toString()
        }

        binding.ivFourthCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (4.0).toString()
        }

        binding.ivFourthCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (3.0).toString()
        }

        binding.ivFifthCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.VISIBLE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.GONE

            binding.tvSave.text = (5.0).toString()
        }

        binding.ivFifthCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (4.0).toString()
        }
    }
}
