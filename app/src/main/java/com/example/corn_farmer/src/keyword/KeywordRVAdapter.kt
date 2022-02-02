package com.example.corn_farmer.src.keyword

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.example.corn_farmer.src.keyword.model.KeywordDto
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ItemKeywordBinding

class KeywordRVAdapter(private val keywordList: List<KeywordDto>) :
    RecyclerView.Adapter<KeywordRVAdapter.Viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding: ItemKeywordBinding =
            ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(keywordList[position])
        holder.itemView.setOnClickListener { //키워드 객체 하나 선택했을 때
            holder.selectKeyword()
        }

    }

    override fun getItemCount(): Int {
        return keywordList.size
    }


    inner class Viewholder(private val binding: ItemKeywordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(keyword: KeywordDto) {
            binding.itemKeywordTv.text = keyword.keyword
        }

        var isSelected: Int = 0

        fun selectKeyword() {
            if (isSelected == 0) {
                keywordSelected()
                isSelected = 1
            } else {
                keywordUnSelected()
                isSelected = 0
            }
        }


        fun keywordSelected() {
            binding.itemKeywordTv.setBackgroundResource(R.drawable.keywordfill)
            binding.itemKeywordTv.setTextSize(Dimension.SP, 20F)
            binding.itemKeywordTv.setTextColor(Color.BLACK)
        }

        fun keywordUnSelected() {
            binding.itemKeywordTv.setBackgroundResource(R.drawable.keyword)
            binding.itemKeywordTv.setTextSize(Dimension.SP, 15F)
            binding.itemKeywordTv.setTextColor(Color.GRAY)
        }

    }
}
//if(keyword.isSelected==0){
//    binding.itemKeywordTv.setBackgroundResource(R.drawable.keyword)
//    binding.itemKeywordTv.setTextSize(Dimension.SP, 15F)
//    binding.itemKeywordTv.setTextColor(Color.GRAY)
//}
//else{
//    binding.itemKeywordTv.setBackgroundResource(R.drawable.keywordfill)
//    binding.itemKeywordTv.setTextSize(Dimension.SP, 20F)
//    binding.itemKeywordTv.setTextColor(Color.BLACK)