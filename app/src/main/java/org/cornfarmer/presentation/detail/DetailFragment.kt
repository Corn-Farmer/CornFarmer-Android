package org.cornfarmer.presentation.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentDetailBinding
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.Social
import org.cornfarmer.data.model.request.RequestMovieLike
import org.cornfarmer.data.model.response.ResponseCommentLike
import org.cornfarmer.data.model.response.ResponseMovieDetail
import org.cornfarmer.data.model.response.ReviewList
import org.cornfarmer.data.repository.DetailService
import org.cornfarmer.data.repository.MovieLikeService
import org.cornfarmer.data.view.DetailFragmentView
import org.cornfarmer.presentation.comment.CommentActivity
import org.cornfarmer.presentation.detail.adapter.CommentRVAdapter
import org.cornfarmer.presentation.detail.adapter.OttServiceRVAdapter
import org.cornfarmer.presentation.home.HomeFragment
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.recommend.RecommendFragment
import org.cornfarmer.presentation.search.SearchFragment
import org.cornfarmer.presentation.search.SearchResultFragment
import org.cornfarmer.presentation.wishlist.WishlistActivity
import org.cornfarmer.util.ext.showToast

class DetailFragment(val movieIdx: Int, val keywordIdx: Int, val keyword: String) :
    Fragment(),
    DetailFragmentView {

    private lateinit var binding: FragmentDetailBinding

    var likeCount = 0
    var moviePhoto = ""
    var movieTitle = ""
    var movieGenre = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val serverToken = sharedPreferences?.getString("servertoken", "")

        val service = DetailService(this, movieIdx, "likeCnt", serverToken!!)
        service.tryGetMovieInfo()
        Log.d("movieIdx", movieIdx.toString())
        initialize()
        reviewSort()

        binding.ivLikeNoColor.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        binding.ivShare.setOnClickListener {
            val defaultFeed = FeedTemplate(
                content = Content(
                    title = movieTitle,
                    description = movieGenre,
                    imageUrl = moviePhoto,
                    link = Link(
                        webUrl = "https://developers.kakao.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                social = Social(
                    likeCount = likeCount
                ),
                buttons = listOf(
                    Button(
                        "앱으로 보기",
                        Link(
//                            androidExecParams = mapOf("key1" to "value1", "key2" to "value2"),
//                            iosExecParams = mapOf("key1" to "value1", "key2" to "value2")
                        )
                    )
                )
            )
            // 피드 메시지 보내기

// 카카오톡 설치여부 확인
            if (LinkClient.instance.isKakaoLinkAvailable(requireContext())) {
                // 카카오톡으로 카카오링크 공유 가능
                LinkClient.instance.defaultTemplate(
                    requireContext(),
                    defaultFeed
                ) { linkResult, error ->
                    if (error != null) {
                        Log.e("test", "카카오링크 보내기 실패", error)
                    } else if (linkResult != null) {
                        Log.d("test", "카카오링크 보내기 성공 ${linkResult.intent}")
                        startActivity(linkResult.intent)

                        // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w("test", "Warning Msg: ${linkResult.warningMsg}")
                        Log.w("test", "Argument Msg: ${linkResult.argumentMsg}")
                    }
                }
            } else {
                // 카카오톡 미설치: 웹 공유 사용 권장
                // 웹 공유 예시 코드
                val sharerUrl = WebSharerClient.instance.defaultTemplateUri(defaultFeed)

                // CustomTabs으로 웹 브라우저 열기

                // 1. CustomTabs으로 Chrome 브라우저 열기
                try {
                    KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
                } catch (e: UnsupportedOperationException) {
                    // Chrome 브라우저가 없을 때 예외처리
                }

                // 2. CustomTabs으로 디바이스 기본 브라우저 열기
                try {
                    KakaoCustomTabsClient.open(requireContext(), sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // 인터넷 브라우저가 없을 때 예외처리
                }
            }
        }

        return binding.root
    }

    private fun initialize() {
        // 후기 작성 버튼
        binding.ivWriteComment.setOnClickListener {
            val intent = Intent(requireContext(), CommentActivity()::class.java)
            intent.putExtra("movieIdx", movieIdx)
            intent.putExtra("keywordIdx", keywordIdx)
            intent.putExtra("keyword", keyword)
            intent.putExtra("moviePhoto", moviePhoto)

            startActivity(intent)
        }

        // 뒤로가기 버튼
        binding.ivBack.setOnClickListener {
            if (keywordIdx == -1) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_main, HomeFragment())
                    .commitAllowingStateLoss()
            } else if (keywordIdx == -2) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_main, SearchResultFragment(keyword))
                    .commitAllowingStateLoss()
            } else {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_main, RecommendFragment(keywordIdx))
                    .commitAllowingStateLoss()
            }
        }

        val mActivity = activity as MainActivity // 검색 버튼
        binding.ivSearch.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        binding.ivLikeColor.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", "")

            if (servertoken == "") {
                activity?.showToast("로그인이 필요한 서비스입니다.")
            } else {
                val service = MovieLikeService(this, movieIdx, servertoken)
                service.tryPutMovieLike()
            }

            binding.ivLikeColor.visibility = View.GONE
            binding.ivLikeNoColorSecond.visibility = View.VISIBLE
            likeCount -= 1
            binding.tvLikeTotal.text = "${likeCount}명이 찜했어요."
        }

        binding.ivLikeNoColorSecond.setOnClickListener {
            val sharedPreferences = context?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", "")

            if (servertoken == "") {
                activity?.showToast("로그인이 필요한 서비스입니다.")
            } else {
                val service = MovieLikeService(this, movieIdx, servertoken)
                service.tryPutMovieLike()
                binding.ivLikeColor.visibility = View.VISIBLE
                binding.ivLikeNoColorSecond.visibility = View.GONE
                likeCount += 1
                binding.tvLikeTotal.text = "${likeCount}명이 찜했어요."
            }
        }
    }

    override fun onGetDetailSuccess(response: ResponseMovieDetail) {
        Log.d("detailMovie", response.toString())
        Log.d("LeeMovie", response.result!!.reviewList.toString())
        if (response.isSuccess) {
            // 영화 정보
            val movieInfo = response.result
            Log.d("detail!@#!@#", "dsdfsdf  $movieInfo")

            Log.d("detail!@#!@#", movieInfo!!.director)

            binding.tvDirectorName.text = movieInfo.director

            for (i in 0 until movieInfo.actorList.size) {
                binding.tvAppearName.text = movieInfo.actorList[i]
            }

            binding.tvMovieTitle.text = movieInfo.movieName
            binding.tvMovieRelease.text = "(${movieInfo.releaseYear})"
            binding.tvGenreTitle.text =
                movieInfo.movieGenreList.joinToString(separator = ",")
            binding.tvStory.text = movieInfo.synopsis
            binding.tvLikeTotal.text = "${movieInfo.likeCnt}명이 찜했어요."
            likeCount = movieInfo.likeCnt
            movieGenre =
                "#" + movieInfo.movieGenreList.joinToString(separator = " #") // 공유하기에 쓰이는 변수
            movieTitle = movieInfo.movieName
            setViewMore(binding.tvStory, binding.tvMore)
            Glide.with(this).load(movieInfo.moviePhotoList[0]).into(binding.ivImage)
            moviePhoto = movieInfo.moviePhotoList[0]

            if (movieInfo.liked) {
                binding.ivLikeColor.visibility = View.VISIBLE
                binding.ivLikeNoColorSecond.visibility = View.GONE
            } else {
                binding.ivLikeColor.visibility = View.GONE
                binding.ivLikeNoColorSecond.visibility = View.VISIBLE
            }

            val ottList = movieInfo.ottList

            val ottServiceRvAdapter = OttServiceRVAdapter(ottList!!)
            binding.rcvOtt.adapter = ottServiceRvAdapter
            binding.rcvOtt.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            val sharedPreferences =
                context?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences?.getString("servertoken", "")

            // 댓글 리사이클러뷰
            val reviewInfo = movieInfo.reviewList
            Log.d("reviewList", "$reviewInfo")
            val ReviewRVadapter = CommentRVAdapter(reviewInfo, servertoken!!)
            binding.rcvComment.adapter = ReviewRVadapter
            binding.rcvComment.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            ReviewRVadapter.setCommentLikeBtnClickListener(object :
                    CommentRVAdapter.CommentLikeBtnClickListener { // 후기
                    override fun onHeartClick(
                        getReviewList: ReviewList,
                        position: Int,
                        token: String
                    ) {
                        val sharedPreferences =
                            context?.getSharedPreferences("join", Context.MODE_PRIVATE)
                        val serverToken = sharedPreferences?.getString("servertoken", "")
                        Log.d("servertoken", "$serverToken")
                        if (serverToken == "") {
                            activity?.showToast("로그인이 필요한 서비스입니다.")
                        } else {
                            val service = org.cornfarmer.data.repository.CommentLikeService(
                                this@DetailFragment,
                                movieInfo!!.reviewList[position].reviewIdx,
                                serverToken!!
                            )
                            service.tryPutCommetLike()
                        }
                    }
                })
        } else {
            activity?.showToast("영화 정보를 불러오는 데 실패했습니다.")
        }
    }

    override fun onGetDetailFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
        Log.d("detailMovieInfo", message)
    }

    override fun onPutCommentLikeSuccess(response: ResponseCommentLike) {
        activity?.showToast(response.result.msg)
    }

    override fun onPutCommentLikeFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
    }

    override fun onPutMovieLikeSuccess(response: RequestMovieLike) {
        activity?.showToast(response.result.msg)
    }

    override fun onPutMovieLikeFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
    }

    fun reviewSort() {
        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val serverToken = sharedPreferences?.getString("servertoken", "")
        var sort = ""

        binding.tvRecent.setOnClickListener {
            sort = "recent"
            binding.tvRecent.setTextColor(Color.BLACK)
            binding.tvSortLike.setTextColor(Color.LTGRAY)
            val service = DetailService(this, movieIdx, sort, serverToken!!)
            service.tryGetMovieInfo()
        }

        binding.tvSortLike.setOnClickListener {
            sort = "likeCnt"
            binding.tvRecent.setTextColor(Color.LTGRAY)
            binding.tvSortLike.setTextColor(Color.BLACK)
            val service = DetailService(this, movieIdx, sort, serverToken!!)
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
                        binding.tvLoss.visibility = View.VISIBLE
                    }

                    binding.tvLoss.setOnClickListener {
                        contentTextView.maxLines = 5
                        viewMoreTextView.visibility = View.VISIBLE
                        binding.tvLoss.visibility = View.GONE
                    }
                }
            }
        }
    }
}
