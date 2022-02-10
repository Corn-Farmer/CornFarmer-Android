package com.example.corn_farmer.src.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.recommend.RecommendFragment
import com.example.corn_farmer.src.detail.model.getMovieDetailAPI
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.comment.CommentFragment
import com.example.corn_farmer.src.detail.model.getCommentLike
import com.example.corn_farmer.src.detail.model.getReviewList
import com.example.corn_farmer.src.detail.model.putMovieLike
import com.example.corn_farmer.src.home.HomeFragment
import com.example.corn_farmer.src.search.SearchFragment
import com.example.corn_farmer.src.search_result.SearchResultFragment
import com.example.corn_farmer.src.wishlist.WishlistActivity
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentDetailBinding

class DetailFragment(val movieIdx: Int, val keywordIdx: Int, val keyword: String): Fragment(), DetailFragmentView {
    lateinit var binding: FragmentDetailBinding
    var likeCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        var serverToken = sharedPreferences?.getString("servertoken", "")

        var service = DetailService(this, movieIdx, "likeCnt", serverToken!!)
        service.tryGetMovieInfo()
        Log.d("movieIdx", movieIdx.toString())
        initialize()
        reviewSort()

        binding.detailHeartBtnIv.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        return binding.root
    }

    fun initialize() {
        // 댓글 작성 버튼
        binding.detailPlusCommentIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, CommentFragment(movieIdx, keywordIdx, keyword))
                .commitAllowingStateLoss()
        }

        // 뒤로가기 버튼
        binding.detailPreviousBtnIv.setOnClickListener {
            if (keywordIdx == -1) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, HomeFragment())
                    .commitAllowingStateLoss()
            } else if (keywordIdx == -2) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, SearchResultFragment(keyword))
                    .commitAllowingStateLoss()
            } else {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, RecommendFragment(keywordIdx))
                    .commitAllowingStateLoss()
            }
        }

        val mActivity = activity as MainActivity //검색 버튼
        binding.detailSearchBtnIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        binding.detailMovieLikeOnBtnIv.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", "")

            if (servertoken == "") {
                Toast.makeText(context, "유저 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
            } else {
                var service = MovieLikeService(this, movieIdx, servertoken)
                service.tryPutMovieLike()
            }

            binding.detailMovieLikeOnBtnIv.visibility = View.GONE
            binding.detailMovieLikeOffBtnIv.visibility = View.VISIBLE
            likeCount = likeCount - 1
            binding.detailNumberOfLikeTv.text = "${likeCount}명이 찜했어요."
        }

        binding.detailMovieLikeOffBtnIv.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", "")

            if (servertoken == "") {
                Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
            } else {
                var service = MovieLikeService(this, movieIdx, servertoken)
                service.tryPutMovieLike()
                binding.detailMovieLikeOnBtnIv.visibility = View.VISIBLE
                binding.detailMovieLikeOffBtnIv.visibility = View.GONE
                likeCount = likeCount + 1
                binding.detailNumberOfLikeTv.text = "${likeCount}명이 찜했어요."
            }
        }
    }


    override fun onGetDetailSuccess(response: getMovieDetailAPI) {
        Log.d("detailMovie", response.toString())
        if (response!!.isSuccess) {
            // 영화 정보
            val movieInfo = response!!.result
            Log.d("detail!@#!@#", "dsdfsdf  ${movieInfo}")
            binding.detailMovieTitleTv.text = movieInfo!!.movieName
            binding.detailMovieReleaseTv.text = "(${movieInfo.releaseYear.toString()})"
            binding.detailMovieGenreTv.text =
                movieInfo.movieGenreList?.joinToString(separator = ",")
            binding.detailMovieStoryTv.text = movieInfo.synopsis
            binding.detailNumberOfLikeTv.text = "${movieInfo?.likeCnt}명이 찜했어요."
            likeCount = movieInfo?.likeCnt

            setViewMore(binding.detailMovieStoryTv, binding.viewMore)
            Glide.with(this!!).load(movieInfo!!.moviePhotoList[0]).into(binding.detailMovieImageIv)

            if (movieInfo.liked) {
                binding.detailMovieLikeOnBtnIv.visibility = View.VISIBLE
                binding.detailMovieLikeOffBtnIv.visibility = View.GONE
            } else {
                binding.detailMovieLikeOnBtnIv.visibility = View.GONE
                binding.detailMovieLikeOffBtnIv.visibility = View.VISIBLE
            }

            // ott 정보 리사이클러뷰
            val ottList = movieInfo!!.ottList

            val OttserviceRvAdapter = OttserviceRVAdapter(ottList!!)
            binding.detailOttServiceRv.adapter = OttserviceRvAdapter
            binding.detailOttServiceRv.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            // 댓글 리사이클러뷰
            val reviewInfo = movieInfo!!.reviewList
            Log.d("reviewList", "${reviewInfo}")
            val ReviewRVadapter = CommentRVAdapter(reviewInfo)
            binding.detailCommentRV.adapter = ReviewRVadapter
            binding.detailCommentRV.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            ReviewRVadapter.setCommentLikeBtnClickListener(object :
                CommentRVAdapter.CommentLikeBtnClickListener { //후기
                override fun onHeartClick(getReviewList: getReviewList, position: Int) {

                    val sharedPreferences =
                        context?.getSharedPreferences("join", Context.MODE_PRIVATE)
                    val servertoken = sharedPreferences?.getString("servertoken", "")

                    if (servertoken == "") {
                        Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        var service = CommentLikeService(
                            this@DetailFragment,
                            movieInfo!!.reviewList[position].reviewIdx,
                            servertoken!!
                        )
                        service.tryPutCommetLike()
                    }
                }
            })
        } else {
            Toast.makeText(context, "영화 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetDetailFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
        Log.d("detailMovieInfo", message.toString())
    }

    override fun onPutCommentLikeSuccess(response: getCommentLike) {
        Toast.makeText(context, response.result.msg, Toast.LENGTH_SHORT).show()
    }

    override fun onPutCommentLikeFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }


    override fun onPutMovieLikeSuccess(response: putMovieLike) {
        Toast.makeText(context, response.result.msg, Toast.LENGTH_SHORT).show()
    }

    override fun onPutMovieLikeFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    fun reviewSort() {
        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        var serverToken = sharedPreferences?.getString("servertoken", "")
        var sort: String = ""

        binding.detailReviewSortRecentTv.setOnClickListener {
            sort = "recent"
            binding.detailReviewSortRecentTv.setTextColor(Color.BLACK)
            binding.detailReviewSortLikeTv.setTextColor(Color.LTGRAY)
            var service = DetailService(this, movieIdx, sort, serverToken!!)
            service.tryGetMovieInfo()
        }

        binding.detailReviewSortLikeTv.setOnClickListener {
            sort = "likeCnt"
            binding.detailReviewSortRecentTv.setTextColor(Color.LTGRAY)
            binding.detailReviewSortLikeTv.setTextColor(Color.BLACK)
            var service = DetailService(this, movieIdx, sort, serverToken!!)
            service.tryGetMovieInfo()
        }


    }

    private fun setViewMore(contentTextView: TextView, viewMoreTextView: TextView) {
        // getEllipsisCount()을 통한 더보기 표시 및 구현
        contentTextView.post {
            val lineCount = contentTextView.layout.lineCount
            if (lineCount > 0) {
                if (contentTextView.layout.getEllipsisCount(lineCount - 1) > 0) {
                    // 더보기 표시
                    viewMoreTextView.visibility = View.VISIBLE

                    // 더보기 클릭 이벤트
                    viewMoreTextView.setOnClickListener {
                        contentTextView.maxLines = Int.MAX_VALUE
                        viewMoreTextView.visibility = View.GONE
                        binding.viewLoss.visibility = View.VISIBLE
                    }

                    binding.viewLoss.setOnClickListener {
                        contentTextView.maxLines = 5
                        viewMoreTextView.visibility = View.VISIBLE
                        binding.viewLoss.visibility = View.GONE
                    }

                }
            }
        }

    }
}