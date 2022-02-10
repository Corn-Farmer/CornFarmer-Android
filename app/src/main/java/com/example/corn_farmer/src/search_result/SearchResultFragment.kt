package com.example.corn_farmer.src.search_result

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.search.SearchFragment
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.corn_farmer.src.home.model.MovieDto
import com.example.corn_farmer.src.loading.CustomLoadingDialog
import com.example.corn_farmer.src.search_result.model.SearchResultResponse
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentSearchResultBinding

class SearchResultFragment(var keyword : String?) : Fragment(),SearchResultFragmentView {
    lateinit var binding: FragmentSearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        },1500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        val mActivity = activity as MainActivity //메인 액티비티


        binding.searchBackIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        binding.searchSearchIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }
        binding.searchReviewSortLikeTv.setTextColor(Color.LTGRAY)
        binding.searchReviewSortRateTv.setTextColor(Color.LTGRAY)
        binding.searchReviewSortRecentTv.setTextColor(Color.BLACK)
        var service = SearchResultService(this,keyword,"recent")
        service.tryGetSearchResultList()

        binding.searchReviewSortLikeTv.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.setCancelable(false)
            loadingAnimDialog.setCanceledOnTouchOutside(false)
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            },1500)//좋아요순
            binding.searchReviewSortLikeTv.setTextColor(Color.BLACK)
            binding.searchReviewSortRateTv.setTextColor(Color.LTGRAY)
            binding.searchReviewSortRecentTv.setTextColor(Color.LTGRAY)
            var service = SearchResultService(this,keyword,"likeCnt")
            service.tryGetSearchResultList()
        }
        binding.searchReviewSortRecentTv.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.setCancelable(false)
            loadingAnimDialog.setCanceledOnTouchOutside(false)
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            },1500)// 최신순
            binding.searchReviewSortLikeTv.setTextColor(Color.LTGRAY)
            binding.searchReviewSortRateTv.setTextColor(Color.LTGRAY)
            binding.searchReviewSortRecentTv.setTextColor(Color.BLACK)
            var service = SearchResultService(this,keyword,"recent")
            service.tryGetSearchResultList()
        }
        binding.searchReviewSortRateTv.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.setCancelable(false)
            loadingAnimDialog.setCanceledOnTouchOutside(false)
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            },1500)//후기많은순
            binding.searchReviewSortLikeTv.setTextColor(Color.LTGRAY)
            binding.searchReviewSortRateTv.setTextColor(Color.BLACK)
            binding.searchReviewSortRecentTv.setTextColor(Color.LTGRAY)
            var service = SearchResultService(this,keyword,"review")
            service.tryGetSearchResultList()
        }



        binding.searchResultMovieTitleTv.text = "\""+keyword+"\""
        return binding.root
    }

    override fun onGetSearchResultSuccess(response: SearchResultResponse) {
        val searchResultList = response!!.result!!

        Log.d("SearchResult", "$searchResultList")

        val searchResultRVAdapter = SearchResultRVAdapter(searchResultList)
        binding.searchOttItemRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.searchOttItemRecyclerview.adapter = searchResultRVAdapter
        searchResultRVAdapter.setMyItemClickListener(object : SearchResultRVAdapter.MyItemClickListener {
            override fun onItemClick(movieList: MovieDto, position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, DetailFragment(movieList.movieIdx!!, -2, keyword!!))
                    .commitAllowingStateLoss()
            }
        })
    }

    override fun onGetSearchResultFailure(message: String) {
        Log.d("SearchResult", "검색결과 가져오기 실패")
    }
}