package com.example.scenespotnersion2.main.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.databinding.SeasonsFragmentItemBinding
import com.example.scenespotnersion2.remote.data.SeasonDBItem

class SeasonsAdapter(private var seasonsList: List<SeasonDBItem>) : RecyclerView.Adapter<SeasonsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding : SeasonsFragmentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = SeasonsFragmentItemBinding.inflate((LayoutInflater.from(parent.context)),parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
            return seasonsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val seasons = seasonsList[position]
        holder.apply {
            binding.apply {
                Glide.with(itemView.context)
                    .load(seasons.image?.medium)
                    .into(ivMovieItemImage)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSeasonsList(newSeasonsList:List<SeasonDBItem>){
        seasonsList = newSeasonsList
        notifyDataSetChanged()
    }

}