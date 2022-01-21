package com.example.cornfarmer_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.databinding.FragmentDetailBinding

class DetailFragment: Fragment() {
    lateinit var binding : FragmentDetailBinding
    private var commentList = ArrayList<comment>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        commentList.apply {
            add(comment("sejong", "it is great"))
            add(comment("ansan", "worst"))
            add(comment("umc", "android is good"))
            add(comment("seongminkim", "error too much i'm sad"))
        }

        val RVadapter = CommentRVAdapter(commentList)
        binding.detailCommentRV.adapter = RVadapter
        binding.detailCommentRV.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )


        return binding.root
    }
}