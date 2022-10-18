package org.cornfarmer.persentation.keyword.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ItemKeywordBinding
import org.cornfarmer.data.model.response.KeywordDto

class KeywordRVAdapter(private val keywordList: List<KeywordDto>) :
    RecyclerView.Adapter<KeywordRVAdapter.Viewholder>() {

    interface MyItemClickListener {
        fun onItemClick(KeyworDto: KeywordDto, positon: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding: ItemKeywordBinding =
            ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(keywordList[position])
        holder.itemView.setOnClickListener { // 키워드 객체 하나 선택했을 때
            holder.selectKeyword()
            myItemClickListener.onItemClick(keywordList[position], position)
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
            binding.itemKeywordTv.setTextSize(Dimension.SP, 20F)
            binding.itemKeywordTv.setTextColor(Color.GRAY)
        }
    }
}
