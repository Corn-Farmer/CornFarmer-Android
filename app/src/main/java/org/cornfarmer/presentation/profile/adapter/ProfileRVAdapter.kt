package org.cornfarmer.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.cornfarmer.data.model.response.ProfileOtt
import org.cornfarmer.databinding.ItemProfileOttBinding

class ProfileRVAdapter(private val ottList: List<ProfileOtt>) :
    RecyclerView.Adapter<ProfileRVAdapter.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding: ItemProfileOttBinding =
            ItemProfileOttBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(ottList[position])
    }

    override fun getItemCount(): Int {
        return ottList.size
    }

    inner class Viewholder(val binding: ItemProfileOttBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ott: ProfileOtt) {
            Glide.with(itemView)
                .load(ott.photo)
                .into(binding.ivOtt)
        }
    }
}
