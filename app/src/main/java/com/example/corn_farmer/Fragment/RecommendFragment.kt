package com.example.cornfarmer_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cornfarmer_android.databinding.FragmentRecommendBinding

class RecommendFragment: Fragment() {
    lateinit var binding : FragmentRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        binding.recommendMovieImage1Iv.setOnClickListener {
        }

        binding.recommendMovieImage2Iv.setOnClickListener {

        }

        binding.recommendMovieImage3Iv.setOnClickListener {

        }
        return binding.root
    }
}