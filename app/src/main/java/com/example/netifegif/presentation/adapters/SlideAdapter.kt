package com.example.netifegif.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netifegif.R
import com.example.netifegif.domain.models.Giphy


class SlideAdapter (
    private val gifsArray: Array<Giphy>
) : RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding: View = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.displayGif(gifsArray[position])
    }

    override fun getItemCount(): Int {
        return gifsArray.size
    }

    class SlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun displayGif(item: Giphy) {
            Glide.with(itemView.context)
                .asGif()
                .load(item.url_large)
                .into(itemView.findViewById(R.id.imageViewGif))
        }
    }
}
