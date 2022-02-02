package com.example.corn_farmer.src.search_result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.src.search_result.model.SearchResultResponse
import com.example.cornfarmer_android.databinding.FragmentSearchResultBinding

class SearchResultFragment(var keyword : String?) : Fragment(),SearchResultFragmentView {
    lateinit var binding: FragmentSearchResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)

        var service = SearchResultService(this,keyword,1)
        service.tryGetSearchResultList()


        binding.searchResultMovieTitleTv.text = "\""+keyword+"\""
        return binding.root
    }

    override fun onGetSearchResultSuccess(response: SearchResultResponse) {
        val searchResultList = response!!.result!!

        Log.d("SearchResult", "$searchResultList")

        val searchResultRVAdapter = SearchResultRVAdapter(searchResultList)
        binding.searchOttItemRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        binding.searchOttItemRecyclerview.adapter = searchResultRVAdapter
    }

    override fun onGetSearchResultFailure(message: String) {
        Log.d("SearchResult", "검색결과 가져오기 실패")
    }
}