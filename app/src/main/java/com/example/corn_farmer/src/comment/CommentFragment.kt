package com.example.corn_farmer.src.comment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.corn_farmer.src.comment.model.getReviewAPI
import com.example.corn_farmer.src.comment.model.sendReviewAPI
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.corn_farmer.src.home.HomeService
import com.example.corn_farmer.src.search.SearchFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentCommentBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CommentFragment(val movieIdx : Int, val keywordIdx: Int, val keyword: String) : Fragment(), CommentFragmentView {
    lateinit var binding : FragmentCommentBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)

        getRate()
        initialize()

        return binding.root
    }

    override fun onPostReviewSuccess(response: getReviewAPI) {

        Log.d("comment", "${response}")

        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, DetailFragment(movieIdx, keywordIdx, keyword))
            .commitAllowingStateLoss()
    }

    override fun onPostReviewFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    fun initialize() {
        binding.commentCompleteBtnIv.setOnClickListener {
            val review = sendReviewAPI(movieIdx, binding.commentEdittext.text.toString() , binding.commentRateSaveTv.text.toString().toDouble())

            val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", null)

            if (servertoken == null) {
                Toast.makeText(context, "유저 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
            } else {

                var service = CommentService(this, review, servertoken)
                service.tryPostReview()
                Toast.makeText(context, "리뷰가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.commentPreviousBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, DetailFragment(movieIdx, keywordIdx, keyword))
                .commitAllowingStateLoss()
        }

        binding.commentSearchBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, SearchFragment())
                .commitAllowingStateLoss()
        }
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