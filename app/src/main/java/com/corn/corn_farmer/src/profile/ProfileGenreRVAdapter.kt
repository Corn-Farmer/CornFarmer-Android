package com.corn.corn_farmer.src.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.corn.corn_farmer.src.profile.model.ProfileGenre
import com.corn.cornfarmer_android.databinding.ItemProfileGenreBinding

class ProfileGenreRVAdapter(private val genreList : List<ProfileGenre>) : RecyclerView.Adapter<ProfileGenreRVAdapter.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileGenreRVAdapter.Viewholder {
        val binding : ItemProfileGenreBinding = ItemProfileGenreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ProfileGenreRVAdapter.Viewholder, position: Int) {
        holder.bind(genreList[position])
    }

    override fun getItemCount(): Int {
        return genreList.size
    }


    inner class Viewholder(val binding : ItemProfileGenreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(genre : ProfileGenre){
            binding.profileGenreTv.text = genre.genre_name
        }
    }
}
