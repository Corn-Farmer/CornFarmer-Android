package com.example.cornfarmer_android

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.corn_farmer.Item.getReviewAPI
import com.example.corn_farmer.Item.sendReviewAPI
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.reviewService
import com.example.cornfarmer_android.databinding.FragmentCommentBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CommentFragment(val movieIdx : Int) : Fragment() {
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
            sendReview(review)
        }
        return binding.root
    }

    fun sendReview(review : sendReviewAPI) {
        if (binding.commentEdittext.text.toString() == "" || binding.commentEdittext.text.toString() == " ") {
            Toast.makeText(context, "리뷰가 작성되지 않았습니다.", Toast.LENGTH_SHORT).show()
        } else {
            val retrofit = Retrofit.Builder().baseUrl("http://3.34.223.58:9000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
            val reviewService = retrofit.create(reviewService::class.java)

            reviewService.sendReview(review).enqueue(object : Callback<getReviewAPI>{
                override fun onResponse(call: Call<getReviewAPI>, response: Response<getReviewAPI>) {
                    Toast.makeText(context, "리뷰가 저장되었습니다.", Toast.LENGTH_SHORT).show()

                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, DetailFragment(movieIdx))
                        .commitAllowingStateLoss()
                }

                override fun onFailure(call: Call<getReviewAPI>, t: Throwable) {
                    Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}