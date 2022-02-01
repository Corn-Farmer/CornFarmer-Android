package com.example.corn_farmer.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.Item.Keyword
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.RVAdapter.KeywordRVAdapter
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentKeywordBinding

class KeywordFragment : Fragment() {

    lateinit var binding: FragmentKeywordBinding
    private var keywordDatas = ArrayList<Keyword>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeywordBinding.inflate(inflater, container, false)
        keywordDatas.apply {
            add(Keyword("# 혼자 있고 싶을 때",  0))
            add(Keyword("# 애인이랑 싸웠을 때",  0))
            add(Keyword("# 춤추고 싶을 때",  0))
            add(Keyword("# 따뜻한 연말",  0))
            add(Keyword("# 마음껏 울고 싶을 때", 0))
            add(Keyword("# 퇴근 후 지쳤을 때",  0))
        }

        val keywordRVAdapter = KeywordRVAdapter(keywordDatas)
        binding.keywordKeywordRecyclerview.adapter = keywordRVAdapter
        binding.keywordKeywordRecyclerview.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        keywordRVAdapter.setMyItemClickListener(object : KeywordRVAdapter.MyItemClickListener {
            override fun onItemClick(keyword: Keyword) {
                selectKeyword(keyword)
            }


        })

        binding.keywordCompleteBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, RecommendFragment())
                .commitAllowingStateLoss()
        }



        return binding.root
    }

    private fun selectKeyword(keyword: Keyword){
        if(keyword.isSelected == 0 ){
            keyword.isSelected = 1
        }
        else{
            keyword.isSelected = 0
        }


    }


}

