package com.example.scenespotnersion2.main.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.SearchItemBinding
import com.example.scenespotnersion2.remote.data.SeriesDBItem
import com.google.firebase.database.FirebaseDatabase
import jp.wasabeef.glide.transformations.BlurTransformation

class SearchAdapter(private var searchList: List<SeriesDBItem>) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val searchItem = searchList[position]
        holder.apply {
            binding.apply {
                tvSearchSeriesName.text = searchItem.name
                "Runtime : ${searchItem.runtime.toString()} minute".also { tvSearchRunTime.text = it }
                ("IMDB ${searchItem.rating?.average}").also { tvRating.text = it }

                Glide.with(itemView.context)
                    .load(searchItem.image?.medium)
                    .transform(BlurTransformation(5,3))
                    .into(ivSearchImageBackground)

                Glide.with(itemView.context)
                    .load(searchItem.image?.medium)
                    .placeholder(R.drawable.series_placeholder)
                    .into(ivSearchSeriesImage)

                ivSearchBookMark.setOnClickListener {

                }
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setSearchList(newSearchList:List<SeriesDBItem>){
        searchList = newSearchList
        notifyDataSetChanged()
    }
}