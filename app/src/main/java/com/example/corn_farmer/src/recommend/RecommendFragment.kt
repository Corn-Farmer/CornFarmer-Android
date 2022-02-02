package com.example.corn_farmer.src.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.src.recommend.model.Ott
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    lateinit var binding : FragmentRecommendBinding
    private var ottDatas = ArrayList<Ott>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)


        //데이터 리스트 생성
        ottDatas.apply {
            add(Ott("올라프가 전해요","애니메이션,가족,판타지,코미디",false,12, R.drawable.olaf))
            add(Ott("나홀로 집에","코미디,가족",true,10, R.drawable.kevin))
            add(Ott("왓이프","애니메이션,판타지,SF",false,20, R.drawable.whatif))
        }


        val ottRVAdapter = OttRVAdapter(ottDatas)
        binding.recommendMovieRV.adapter = ottRVAdapter
        binding.recommendMovieRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        ottRVAdapter.setMyItemClickListener(object : OttRVAdapter.MyItemClickListener {
            override fun onItemClick(ott: Ott, position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, DetailFragment(position+15))
                    .commitAllowingStateLoss()
            }
        })


        return binding.root
    }
}