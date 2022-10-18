package org.cornfarmer.persentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ItemOttServiceBinding
import org.cornfarmer.data.model.response.OttList

class OttServiceRVAdapter(private val imageList: ArrayList<OttList>) :
    RecyclerView.Adapter<OttServiceRVAdapter.Viewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val binding: ItemOttServiceBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_ott_service,
            parent,
            false
        )

        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class Viewholder(val binding: ItemOttServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgsrc: OttList) {
            Glide.with(itemView).load(imgsrc.ottImage).into(binding.detailOttServiceIv)
        }
    }
}
