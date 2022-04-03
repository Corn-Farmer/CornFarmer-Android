package com.corn.corn_farmer.src.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.corn_farmer.src.detail.model.getOttList
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ItemOttServiceBinding

class OttServiceRVAdapter(private val imageList : ArrayList<getOttList>)  : RecyclerView.Adapter<OttServiceRVAdapter.Viewholder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val binding : ItemOttServiceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_ott_service, parent, false)

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class Viewholder(val binding : ItemOttServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imgsrc: getOttList) {
            Glide.with(itemView).load(imgsrc.ottImage).into(binding.detailOttServiceIv)
        }
    }
}