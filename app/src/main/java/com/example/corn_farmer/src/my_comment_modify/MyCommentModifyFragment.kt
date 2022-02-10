package com.example.corn_farmer.src.my_comment_modify

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.my_comment.MyCommentFragment
import com.example.corn_farmer.src.my_comment.MyCommentFragmentView
import com.example.corn_farmer.src.my_comment.MyCommentService
import com.example.corn_farmer.src.my_comment.model.getMyComment
import com.example.corn_farmer.src.my_comment_modify.model.getCommnetModifyAPI
import com.example.corn_farmer.src.my_comment_modify.model.sendModifyComment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentMyCommentModifyBinding

class MyCommentModifyFragment(val reviewInfo : sendModifyComment, val reviewIdx : Int, val nickname : String) : Fragment(), MyCommentModifyFragmentView {
    lateinit var binding : FragmentMyCommentModifyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCommentModifyBinding.inflate(inflater, container, false)

        binding.commentEdittext.setText(reviewInfo.content)

        setRate()
        getRate()

        binding.commentCompleteBtnIv.setOnClickListener {
            val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", null)
            val newReview = sendModifyComment(binding.commentEdittext.text.toString(), binding.commentRateSaveTv.text.toString().toDouble())
            val service = MyCommentModifyService(this, newReview, reviewIdx, servertoken!!)
            service.tryPutMyCommentModify()
        }

        binding.commentCancelBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, MyCommentFragment(nickname))
                .commitAllowingStateLoss()

        }

        return binding.root
    }

    fun setRate() {
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
        }else if (reviewInfo.rate <= 2.1) {
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

    override fun onPutMyCommentModifySuccess(response: getCommnetModifyAPI) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, MyCommentFragment(nickname))
            .commitAllowingStateLoss()
        Toast.makeText(context, "후기를 수정하였습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onPutMyCommentModifyFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    fun getRate() {


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