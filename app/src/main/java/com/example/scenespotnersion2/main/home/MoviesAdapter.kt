package com.example.scenespotnersion2.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.remote.data.MovieResponse

class MoviesAdapter(private var movieList: List<MovieResponse?>) :
    RecyclerView.Adapter<MoviesAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.ivMovieItemImage)
        val movieRating : TextView = itemView.findViewById(R.id.tvMovieItemRating)
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
        holder.movieRating.text = movie?.rating.toString()
        Glide.with(holder.itemView.context)
            .load(movie?.banner)
            .into(holder.movieImage)
    }

    fun setMovies(newMovieList: List<MovieResponse?>){
        movieList = newMovieList
        notifyDataSetChanged()
    }
}