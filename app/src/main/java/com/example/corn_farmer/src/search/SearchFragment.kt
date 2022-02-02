package com.example.corn_farmer.src.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.cornfarmer_android.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        binding.searchSearchButtonIv.setOnClickListener {
           var searchMovie : String = binding.searchSearchInputEt.text.toString()
            Log.d("search","$searchMovie")

        }




        return binding.root
    }
}