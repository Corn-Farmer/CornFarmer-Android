package org.cornfarmer.presentation.search

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentSearchResultBinding
import org.cornfarmer.data.model.response.MovieDto
import org.cornfarmer.data.repository.SearchResultService
import org.cornfarmer.data.view.SearchResultFragmentView
import org.cornfarmer.presentation.detail.DetailFragment
import org.cornfarmer.presentation.loading.CustomLoadingDialog
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.search.adapter.SearchResultRVAdapter

class SearchResultFragment(var keyword: String?) : Fragment(), SearchResultFragmentView {

    private lateinit var binding: FragmentSearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        }, 1500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)

        val getSharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val serverToken = getSharedPreferences?.getString("servertoken", "")

        val mActivity = activity as MainActivity // 메인 액티비티

        binding.ivBack.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        binding.ivSearch.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }
        binding.tvSortLike.setTextColor(Color.LTGRAY)
        binding.tvSortRate.setTextColor(Color.LTGRAY)
        binding.tvRecent.setTextColor(Color.BLACK)
        val service = SearchResultService(this, keyword, "recent", serverToken!!)
        service.tryGetSearchResultList()

        binding.tvSortLike.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.setCancelable(false)
            loadingAnimDialog.setCanceledOnTouchOutside(false)
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            }, 1500) // 좋아요순
            binding.tvSortLike.setTextColor(Color.BLACK)
            binding.tvSortRate.setTextColor(Color.LTGRAY)
            binding.tvRecent.setTextColor(Color.LTGRAY)
            val service = SearchResultService(this, keyword, "likeCnt", serverToken!!)
            service.tryGetSearchResultList()
        }
        binding.tvRecent.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.setCancelable(false)
            loadingAnimDialog.setCanceledOnTouchOutside(false)
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            }, 1500) // 최신순
            binding.tvSortLike.setTextColor(Color.LTGRAY)
            binding.tvSortRate.setTextColor(Color.LTGRAY)
            binding.tvRecent.setTextColor(Color.BLACK)
            val service = SearchResultService(this, keyword, "recent", serverToken!!)
            service.tryGetSearchResultList()
        }
        binding.tvSortRate.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.setCancelable(false)
            loadingAnimDialog.setCanceledOnTouchOutside(false)
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            }, 1500) // 후기많은순
            binding.tvSortLike.setTextColor(Color.LTGRAY)
            binding.tvSortRate.setTextColor(Color.BLACK)
            binding.tvRecent.setTextColor(Color.LTGRAY)
            val service = SearchResultService(this, keyword, "review", serverToken!!)
            service.tryGetSearchResultList()
        }

        binding.tvKeyword.text = "\"" + keyword + "\""
        return binding.root
    }

    override fun onGetSearchResultSuccess(response: org.cornfarmer.data.model.response.ResponseSearchResult) {
        val searchResultList = response.result!!

        Log.d("SearchResult", "$searchResultList")

        val searchResultRVAdapter = SearchResultRVAdapter(searchResultList)
        binding.rcvSearch.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcvSearch.adapter = searchResultRVAdapter
        searchResultRVAdapter.setMyItemClickListener(object :
                SearchResultRVAdapter.MyItemClickListener {
                override fun onItemClick(movieList: MovieDto, position: Int) {
                    val mActivity = activity as MainActivity // 메인 액티비티
                    mActivity.callFragment(DetailFragment(movieList.movieIdx!!, -2, keyword!!))
                }
            })
    }

    override fun onGetSearchResultFailure(message: String) {
        Log.d("SearchResult", "검색결과 가져오기 실패")
    }
}
