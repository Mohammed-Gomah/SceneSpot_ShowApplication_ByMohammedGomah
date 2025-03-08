package com.example.scenespotnersion2.main.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.SeasonsItemBinding
import com.example.scenespotnersion2.remote.data.SeasonDBItem

class DetailsSeasonsAdapter(private var seasonsList: List<SeasonDBItem>) :
    RecyclerView.Adapter<DetailsSeasonsAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: SeasonsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = SeasonsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return seasonsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val season = seasonsList[position]

        holder.apply {
            binding.apply {

                "season ${season.number}".also { tvSeasons.text = it }

                ivMovieItemImage.setOnClickListener {
                    val action = DetailsFragmentDirections.actionDetailsFragmentToEpisodeFragment(season.id!!)
                    itemView.findNavController().navigate(action)
                }

                Glide.with(holder.itemView.context)
                    .load(season.image?.medium)
                    .placeholder(R.drawable.series_placeholder)
                    .into(ivMovieItemImage)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSeasons(newSeasonList:List<SeasonDBItem>){
        seasonsList = newSeasonList
        notifyDataSetChanged()
    }
}