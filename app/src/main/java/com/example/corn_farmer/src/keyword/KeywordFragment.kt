package com.example.corn_farmer.src.keyword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.corn_farmer.src.keyword.model.KeywordDto
import com.example.corn_farmer.src.keyword.model.KeywordResponse
import com.example.corn_farmer.src.recommend.RecommendFragment
import com.example.corn_farmer.src.search.SearchFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentKeywordBinding

class KeywordFragment : Fragment(),KeywordFragmentView {

    lateinit var binding: FragmentKeywordBinding
    var list: List<KeywordDto> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordBinding.inflate(inflater, container, false)
        var service = KeywordService(this)
        service.tryGetKeywordList()

        binding.keywordRefreshIv.setOnClickListener{
            service.tryGetKeywordList()
        }

        binding.keywordCompleteBtnIv.setOnClickListener {

        }

        binding.mainSearchIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, SearchFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }


    override fun onGetKeywordListSuccess(response: KeywordResponse) {
        list = response!!.result!!
        Log.d("KeywordFragment", "$list")
        val keywordRVAdapter = KeywordRVAdapter(list)
        binding.keywordKeywordRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.keywordKeywordRecyclerview.adapter = keywordRVAdapter
        keywordRVAdapter.setMyItemClickListener(object : KeywordRVAdapter.MyItemClickListener {
            override fun onItemClick(KeyworDto: KeywordDto, positon: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, RecommendFragment(response.result[positon].keywordIdx!!))
                    .commitAllowingStateLoss()
            }
        })
    }

    override fun onGetKeywordListFailure(message: String) {
        Log.d("KeywordFragment", "실패")
    }
}

