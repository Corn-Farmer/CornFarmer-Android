package com.example.corn_farmer.src.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.recommend.RecommendFragment
import com.example.corn_farmer.src.detail.model.getMovieDetailAPI
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.comment.CommentFragment
import com.example.corn_farmer.src.comment.CommentRVAdapter
import com.example.corn_farmer.src.home.HomeService
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentDetailBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class DetailFragment(val movieIdx: Int): Fragment(),DetailFragmentView {
    lateinit var binding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        var service = DetailService(this,movieIdx,1)
        service.tryGetMovieInfo()
        initialize()

        return binding.root
    }

    fun initialize() {
        // 댓글 작성 버튼
        binding.detailPlusCommentIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, CommentFragment(movieIdx))
                .commitAllowingStateLoss()
        }

        // 뒤로가기 버튼
        binding.detailPreviousBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, RecommendFragment())
                .commitAllowingStateLoss()
        }

    }


    override fun onGetDetailSuccess(response: getMovieDetailAPI) {
        Log.d("detailMovie", response.toString())
        if (response!!.isSuccess) {
            // 영화 정보
            val movieInfo = response!!.result
            binding.detailMovieTitleTv.text = movieInfo?.movieName
            binding.detailMovieReleaseTv.text = "(${movieInfo?.releaseYear.toString()}년 개봉)"
            binding.detailMovieGenreTv.text = movieInfo?.movieGenreList?.joinToString(separator = ",")
            binding.detailMovieStoryTv.text = movieInfo?.synopsis
            binding.detailNumberOfLikeTv.text = "${movieInfo?.likeCnt}명이 찜했어요."
            Glide.with(this!!).load(movieInfo!!.moviePhotoList[0]).into(binding.detailMovieImageIv)

            // ott 정보 리사이클러뷰
            val ottList = movieInfo.ottList

            val OttserviceRvAdapter = OttserviceRVAdapter(ottList)
            binding.detailOttServiceRv.adapter = OttserviceRvAdapter
            binding.detailOttServiceRv.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            // 댓글 리사이클러뷰
            val reviewInfo = movieInfo.reviewList

            val ReviewRVadapter = CommentRVAdapter(reviewInfo)
            binding.detailCommentRV.adapter = ReviewRVadapter
            binding.detailCommentRV.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        } else {
            Toast.makeText(context, "영화 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetDetailFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
        Log.d("detailMovieInfo", message.toString())
    }
}