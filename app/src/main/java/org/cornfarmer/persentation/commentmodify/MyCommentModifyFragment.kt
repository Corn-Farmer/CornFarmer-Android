package org.cornfarmer.persentation.commentmodify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentMyCommentModifyBinding
import org.cornfarmer.data.model.request.RequestModifyComment
import org.cornfarmer.data.model.response.ResponseCommentModify
import org.cornfarmer.data.repository.MyCommentModifyService
import org.cornfarmer.data.view.MyCommentModifyFragmentView
import org.cornfarmer.persentation.main.MainActivity
import org.cornfarmer.persentation.mycomment.MyCommentFragment
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

        binding.commentEdittext.setText(reviewInfo.content)

        setRate()
        getRate()

        binding.commentCompleteBtnIv.setOnClickListener {
            val sharedPreferences =
                this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", null)
            val newReview = RequestModifyComment(
                binding.commentEdittext.text.toString(),
                binding.commentRateSaveTv.text.toString().toDouble()
            )
            val service = MyCommentModifyService(this, newReview, reviewIdx, servertoken!!)
            service.tryPutMyCommentModify()
        }

        binding.commentCancelBtnIv.setOnClickListener {
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
            binding.commentRateCorn1ColorIv.visibility = View.INVISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.VISIBLE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 1.1) {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 2.1) {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 3.1) {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 4.1) {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE
        } else if (reviewInfo.rate <= 5.1) {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.VISIBLE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.GONE
        }
    }

    private fun getRate() {
        binding.commentRateSaveTv.text = reviewInfo.rate.toString()

        binding.commentRateCorn1Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (1.0).toString()
        }

        binding.commentRateCorn1ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.GONE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.VISIBLE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (0.0).toString()
        }

        binding.commentRateCorn2Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (2.0).toString()
        }

        binding.commentRateCorn2ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (1.0).toString()
        }

        binding.commentRateCorn3Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (3.0).toString()
        }

        binding.commentRateCorn3ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (2.0).toString()
        }

        binding.commentRateCorn4Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (4.0).toString()
        }

        binding.commentRateCorn4ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (3.0).toString()
        }

        binding.commentRateCorn5Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.VISIBLE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.GONE

            binding.commentRateSaveTv.text = (5.0).toString()
        }

        binding.commentRateCorn5ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (4.0).toString()
        }
    }
}
