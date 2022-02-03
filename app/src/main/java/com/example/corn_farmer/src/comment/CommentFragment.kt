package com.example.corn_farmer.src.comment

import android.os.Build
import android.os.Bundle
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
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentCommentBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CommentFragment(val movieIdx : Int, val keywordIdx: Int) : Fragment(),CommentFragmentView {
    lateinit var binding : FragmentCommentBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)


        binding.commentCompleteBtnIv.setOnClickListener {
            val review = sendReviewAPI(movieIdx, binding.commentEdittext.text.toString() , 3.53)

            var service = CommentService(this,review)
            service.tryPostReview()
        }
        return binding.root
    }


    override fun onPostReviewSuccess(response: getReviewAPI) {
        Toast.makeText(context, "리뷰가 저장되었습니다.", Toast.LENGTH_SHORT).show()

        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, DetailFragment(movieIdx, keywordIdx))
            .commitAllowingStateLoss()
    }

    override fun onPostReviewFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}