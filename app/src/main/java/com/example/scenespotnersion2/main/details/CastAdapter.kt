package com.example.scenespotnersion2.main.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.CastItemBinding
import com.example.scenespotnersion2.remote.data.CastDBItem
import com.example.scenespotnersion2.remote.data.Person

class CastAdapter(private var castList: List<CastDBItem>) :
    RecyclerView.Adapter<CastAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cast = castList[position]
        holder.apply {
            binding.apply {
                binding.tvActorName.text = cast.person?.name
                binding.tvCharacterName.text = cast.character?.name

                Glide.with(itemView.context)
                    .load(cast.person?.image?.medium)
                    .placeholder(R.drawable.actor_placeholder)
                    .into(ivCastImage)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCast(newCastList:List<CastDBItem>){
        castList = newCastList
        notifyDataSetChanged()
    }
}