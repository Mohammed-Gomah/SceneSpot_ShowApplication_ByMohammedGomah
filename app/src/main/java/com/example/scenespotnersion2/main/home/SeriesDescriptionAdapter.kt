package com.example.scenespotnersion2.main.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.DescriptionItemBinding
import com.example.scenespotnersion2.remote.data.SeriesDBItem
import jp.wasabeef.glide.transformations.BlurTransformation

class SeriesDescriptionAdapter(private var seriesList: List<SeriesDBItem?>) :
    RecyclerView.Adapter<SeriesDescriptionAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: DescriptionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            DescriptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val series = seriesList[position]
        holder.apply {
            binding.apply {
                binding.tvShowDescriptionItemName.text = series?.name
                binding.tvShowDescriptionItemDescription.text =
                    series?.summary?.parseAsHtml(HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            binding.tvDescriptionItemRating.text =
                series?.rating?.average.takeIf { it.toString() != "null" }?.toString() ?: "4.5"
            ("${series?.premiered} ${
                if (series?.ended.isNullOrEmpty()) "and ended yet" else "To ${series?.ended}"
            }").also {
                binding.tvDescriptionItemStartDate.text = it
            }

            binding.btnWatchTrailer.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse((series?.officialSite)))
                itemView.context.startActivity(intent)
            }

            Glide.with(holder.itemView.context)
                .load(series?.image?.medium)
                .placeholder(R.drawable.series_placeholder)
                .centerCrop()
                .into(binding.ivDescriptionItemImage)

            Glide.with(holder.itemView.context)
                .load(series?.image?.medium)
                .placeholder(R.drawable.series_placeholder)
                .transform(BlurTransformation(150, 3))
                .centerCrop()
                .into(binding.ivDescriptionItemBackgroundImage)

        }
    }

    fun setSeries(newMovieList: List<SeriesDBItem?>) {
        seriesList = newMovieList
        notifyDataSetChanged()
    }
}