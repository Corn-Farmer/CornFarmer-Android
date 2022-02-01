package com.example.cornfarmer_android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.corn_farmer.Fragment.RecommendFragment
import com.example.corn_farmer.Item.getMovieDetailAPI
import com.example.corn_farmer.Item.getReviewList
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.detailMovieService
import com.example.cornfarmer_android.databinding.FragmentDetailBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class DetailFragment(val movieIdx: Int): Fragment() {
    lateinit var binding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

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

        // http 통신, 영화 정보 가져와서 채워넣기, 댓글 리사이클러뷰
        getMovieInfo(movieIdx)
    }

    fun getMovieInfo(movieIdx: Int){
        val retrofit = Retrofit.Builder().baseUrl("http://3.34.223.58:9000")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
        val detailMovieService = retrofit.create(detailMovieService::class.java)

        detailMovieService.getMovieInfo(movieIdx, 2).enqueue(object : Callback<getMovieDetailAPI>{
            override fun onResponse(
                call: Call<getMovieDetailAPI>,
                response: Response<getMovieDetailAPI>
            ) {
                Log.d("detailMovie", response.body().toString())
                if (response.body()!!.isSuccess) {
                    // 영화 정보
                    val movieInfo = response.body()!!.result
                    binding.detailMovieTitleTv.text = movieInfo?.movieName
                    binding.detailMovieReleaseTv.text = movieInfo?.releaseYear.toString()
                    binding.detailMovieGenreTv.text = movieInfo?.movieGenreList?.joinToString(separator = ",")
                    binding.detailMovieStoryTv.text = movieInfo?.synopsis
                    binding.detailNumberOfLikeTv.text = "${movieInfo?.likeCnt}명이 찜했어요."
                    Glide.with(context!!).load(movieInfo!!.moviePhotoList[0]).into(binding.detailMovieImageIv)


                    // 댓글 리사이클러뷰
                    val reviewInfo = movieInfo.reviewList

                    val RVadapter = CommentRVAdapter(reviewInfo)
                    binding.detailCommentRV.adapter = RVadapter
                    binding.detailCommentRV.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                } else {
                    Toast.makeText(context, "영화 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<getMovieDetailAPI>, t: Throwable) {
                Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
                Log.d("detailMovieInfo", t.message.toString())
            }
        })
    }
}