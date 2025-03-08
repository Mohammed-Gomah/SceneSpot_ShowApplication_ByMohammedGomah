package com.example.scenespotnersion2.remote.data.data

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.EpisodesItemBinding
import com.example.scenespotnersion2.remote.data.data.episodedata.EpisodeDBItem

class EpisodeAdapter(private var episodesList: List<EpisodeDBItem>) :
    RecyclerView.Adapter<EpisodeAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: EpisodesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = EpisodesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val episode = episodesList[position]
        holder.apply {
            binding.apply {
                tvEpisodeName.text = episode.name
                ("Episode : ${episode.number.toString()}").also { tvEpisodeNumber.text = it }
                tvEpisodeDescription.text =
                    Html.fromHtml(episode.summary, Html.FROM_HTML_MODE_LEGACY)
                ("IMDB ${episode.rating?.average.toString()}").also { tvEpisodeRating.text = it }
                tvEpisodeFirstDate.text = episode.airdate
                tvEpisodeRunTime.text = episode.runtime.toString()
                btnWatch.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(episode.url))
                    itemView.context.startActivity(intent)
                }

                btnWatchTrailer.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(episode.url))
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load(episode.image?.original)
                    .placeholder(R.drawable.series_placeholder)
                    .into(ivEpisodeImage)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEpisodes(newEpisodeList: List<EpisodeDBItem>) {
        episodesList = newEpisodeList
        notifyDataSetChanged()
    }
}