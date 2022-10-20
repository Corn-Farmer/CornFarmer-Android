package org.cornfarmer.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ItemOttServiceBinding
import org.cornfarmer.data.model.response.OttList

class OttServiceRVAdapter(private val imageList: ArrayList<OttList>) :
    RecyclerView.Adapter<OttServiceRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemOttServiceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_ott_service,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(val binding: ItemOttServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgSrc: OttList) {
            Glide.with(itemView).load(imgSrc.ottImage).into(binding.ivOtt)
        }
    }
}
