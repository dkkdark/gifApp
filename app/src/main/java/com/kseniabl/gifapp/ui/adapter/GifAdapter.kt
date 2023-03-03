package com.kseniabl.gifapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.kseniabl.gifapp.Helper.getShimmerDrawable
import com.kseniabl.gifapp.R
import com.kseniabl.gifapp.databinding.ItemGifBinding
import com.kseniabl.gifapp.network.models.GifModel

class GifAdapter : PagingDataAdapter<GifModel, GifAdapter.GifHolder>(DiffCallback()) {

    private var listener: Listener? = null

    class GifHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<GifModel>() {
        override fun areItemsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        val item = getItem(position) ?: return

        holder.binding.apply {
            Glide.with(gifImage.context)
                .asGif()
                .load(item.images.original.url)
                .placeholder(getShimmerDrawable())
                .into(gifImage)
        }
        holder.itemView.setOnClickListener {
            listener?.onItemClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GifHolder(ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    fun setOnClickListener(onClickListener: Listener) {
        listener = onClickListener
    }

    interface Listener {
        fun onItemClick(item: GifModel)
    }
}