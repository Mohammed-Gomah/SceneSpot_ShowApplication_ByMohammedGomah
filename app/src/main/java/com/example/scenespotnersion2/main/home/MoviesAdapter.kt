package com.example.scenespotnersion2.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.remote.data.Result
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MoviesAdapter(private var movieList: List<Result?>) :
    RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.ivMovieItemImage)
        val movieRating : TextView = itemView.findViewById(R.id.tvMovieItemRating)
        val favouriteToggle: ImageView = itemView.findViewById(R.id.ivFavouriteToggle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movie = movieList[position]
        "IMDB ${movie?.rating.toString()}".also { holder.movieRating.text = it }
        Glide.with(holder.itemView.context)
            .load(movie?.banner)
            .placeholder(R.drawable.movies_placeholder)
            .centerCrop()
            .into(holder.movieImage)
    }

    fun setMovies(newMovieList: List<Result?>){
        movieList = newMovieList
        notifyDataSetChanged()
    }
}