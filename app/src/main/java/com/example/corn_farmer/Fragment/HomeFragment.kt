package com.example.corn_farmer.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.Item.Ott
import com.example.corn_farmer.RVAdapter.OttRVAdapter
import com.example.corn_farmer.R
import com.example.corn_farmer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    private var ottDatas = ArrayList<Ott>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        //데이터 리스트 생성
        ottDatas.apply {
            add(Ott("올라프가 전해요","애니메이션,가족,판타지,코미디",false,12,R.drawable.olaf))
            add(Ott("나홀로 집에","코미디,가족",true,10,R.drawable.kevin))
            add(Ott("왓이프","애니메이션,판타지,SF",false,20,R.drawable.whatif))
        }

        val ottRVAdapter = OttRVAdapter(ottDatas)
        binding.homeOttItemRecyclerview.adapter = ottRVAdapter
        binding.homeOttItemRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

            return binding.root
    }
}