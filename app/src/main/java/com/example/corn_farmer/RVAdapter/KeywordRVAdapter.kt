package com.example.corn_farmer.RVAdapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.example.corn_farmer.Item.Keyword
import com.example.corn_farmer.R
import com.example.corn_farmer.databinding.ItemKeywordBinding

class KeywordRVAdapter(private val KeywordList : ArrayList<Keyword>) : RecyclerView.Adapter<KeywordRVAdapter.Viewholder>(){

    interface MyItemClickListener{
        fun onItemClick(keyword: Keyword)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding : ItemKeywordBinding = ItemKeywordBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(KeywordList[position])
        holder.itemView.setOnClickListener{
            myItemClickListener.onItemClick(KeywordList[position])
            holder.bind(KeywordList[position])
        }
    }

    override fun getItemCount(): Int {
        return KeywordList.size
    }


    inner class Viewholder(val binding : ItemKeywordBinding ) : RecyclerView.ViewHolder(binding.root){

        fun bind(keyword: Keyword){
            binding.itemKeywordTv.text = keyword.keyword
            if(keyword.isSelected==0){
                binding.itemKeywordTv.setBackgroundResource(R.drawable.keyword)
                binding.itemKeywordTv.setTextSize(Dimension.SP, 15F)
                binding.itemKeywordTv.setTextColor(Color.GRAY)
            }
            else{
                binding.itemKeywordTv.setBackgroundResource(R.drawable.keywordfill)
                binding.itemKeywordTv.setTextSize(Dimension.SP, 20F)
                binding.itemKeywordTv.setTextColor(Color.BLACK)
            }

        }
    }
}