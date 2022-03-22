package com.corn.corn_farmer.src.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.corn_farmer.src.detail.model.getOttList
import com.corn.cornfarmer_android.databinding.ItemOttServiceBinding

class OttserviceRVAdapter(private val imgsrcList : ArrayList<getOttList>)  : RecyclerView.Adapter<OttserviceRVAdapter.Viewholder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val binding : ItemOttServiceBinding = ItemOttServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(imgsrcList[position])
    }

    override fun getItemCount(): Int {
        return imgsrcList.size
    }

    inner class Viewholder(val binding : ItemOttServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imgsrc: getOttList) {
            Glide.with(itemView).load(imgsrc.ottImage).into(binding.detailOttServiceIv)
        }
    }
}