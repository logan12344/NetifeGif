package com.example.netifegif.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netifegif.databinding.GifItemLayoutBinding
import com.example.netifegif.domain.models.Giphy

class GifsAdapter : RecyclerView.Adapter<GifsAdapter.GifsViewHolder>() {

    inner class GifsViewHolder(val binding: GifItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun displayGif(item: Giphy) {
            Glide.with(itemView.context)
                .asGif()
                .load(item.url_large)
                .into(binding.imageViewGif)
        }
    }

    val list = mutableListOf<Giphy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding =
            GifItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val item = list[position]
        holder.displayGif(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickListener(listener: (Giphy) -> Unit) {
        onItemClickListener = listener
    }
}

private var onItemClickListener: ((Giphy) -> Unit)? = null
