package com.example.netifegif.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netifegif.R
import com.example.netifegif.domain.models.Giphy


class GifsAdapter : RecyclerView.Adapter<GifsAdapter.GifsViewHolder>() {

    private val LIST_ITEM = 0
    private val GRID_ITEM = 1
    var isSwitchView = true

    class GifsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun displayGif(item: Giphy) {
            Glide.with(itemView.context)
                .asGif()
                .load(item.url_large)
                .into(itemView.findViewById(R.id.imageViewGif))
        }
    }

    private val listCallback = object : DiffUtil.ItemCallback<Giphy>() {
        override fun areItemsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Giphy, newItem: Giphy): Boolean {
            return oldItem.url_large == newItem.url_large
        }
    }

    val list = AsyncListDiffer(this, listCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val itemView: View = if (viewType == LIST_ITEM) {
            LayoutInflater.from(parent.context).inflate(R.layout.gif_item_list, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.gif_item_grid, parent, false)
        }

        return GifsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val item = list.currentList[position]
        holder.displayGif(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSwitchView) {
            LIST_ITEM
        } else {
            GRID_ITEM
        }
    }

    fun setItemViewType(toggle: Boolean) {
        isSwitchView = toggle
    }

    override fun getItemCount(): Int = list.currentList.size

    fun setOnItemClickListener(listener: (Giphy) -> Unit) {
        onItemClickListener = listener
    }
}

private var onItemClickListener: ((Giphy) -> Unit)? = null
