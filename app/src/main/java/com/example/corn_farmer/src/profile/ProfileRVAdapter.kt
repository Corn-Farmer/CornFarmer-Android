package com.example.corn_farmer.src.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corn_farmer.src.profile.model.ProfileOtt
import com.example.cornfarmer_android.databinding.ItemProfileOttBinding

class ProfileRVAdapter(private val ottList : List<ProfileOtt>) : RecyclerView.Adapter<ProfileRVAdapter.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileRVAdapter.Viewholder {
        val binding : ItemProfileOttBinding = ItemProfileOttBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ProfileRVAdapter.Viewholder, position: Int) {
        holder.bind(ottList[position])
    }

    override fun getItemCount(): Int {
        return ottList.size
    }


    inner class Viewholder(val binding : ItemProfileOttBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ott : ProfileOtt){
            Glide.with(itemView)
                .load(ott.photo)
                .into(binding.itemProfileOttIv)
        }
    }
}
