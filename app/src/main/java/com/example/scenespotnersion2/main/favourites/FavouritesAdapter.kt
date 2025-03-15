package com.example.scenespotnersion2.main.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.SearchItemBinding
import com.example.scenespotnersion2.remote.data.SeriesDBItem
import jp.wasabeef.glide.transformations.BlurTransformation

class FavouritesAdapter(
    private var favouritesList: List<SeriesDBItem>,
    private val favouritesViewModel: FavouritesViewModel
) :
    Adapter<FavouritesAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val favouriteItem = favouritesList[position]
        holder.apply {
            binding.apply {
                tvSearchSeriesName.text = favouriteItem.name
                "Runtime : ${favouriteItem.runtime} minute".also { tvSearchRunTime.text = it }
                "IMDB : ${favouriteItem.rating?.average}".also { tvRating.text = it }

                favouriteItem.id?.let { showId ->
                    favouritesViewModel.checkFavouriteState(showId.toString()) { isFavourite ->
                        val color = if (isFavourite) ContextCompat.getColor(
                            itemView.context,
                            R.color.orange
                        )
                        else ContextCompat.getColor(itemView.context, R.color.darkBlue)
                        binding.ivSearchBookMark.setColorFilter(color)
                    }
                    ivSearchBookMark.setOnClickListener {
                        favouritesViewModel.toggleFavourite(favouriteItem) { isFavourite ->
                            val color = if (isFavourite) ContextCompat.getColor(itemView.context, R.color.orange)
                            else ContextCompat.getColor(itemView.context, R.color.darkBlue)
                            binding.ivSearchBookMark.setColorFilter(color)
                        }
                    }
                }

                Glide.with(itemView.context)
                    .load(favouriteItem.image?.medium)
                    .into(ivSearchSeriesImage)

                Glide.with(itemView.context)
                    .load(favouriteItem.image?.medium)
                    .transform(BlurTransformation(5, 3))
                    .into(ivSearchImageBackground)

                ivSearchSeriesImage.setOnClickListener {
                    val action =
                        FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(
                            favouriteItem
                        )
                    itemView.findNavController().navigate(action)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }

    fun setFavourites(newFavourites: List<SeriesDBItem>) {
        favouritesList = newFavourites
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: SearchItemBinding) : ViewHolder(binding.root)
}
