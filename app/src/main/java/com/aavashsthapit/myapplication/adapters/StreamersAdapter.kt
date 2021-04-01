package com.aavashsthapit.myapplication.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.databinding.ListItemBinding
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class StreamersAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseStreamersAdapter(R.layout.list_item){

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: StreamerViewHolder, position: Int) {
        val streamer = streamers[position]
        val binding = ListItemBinding.bind(holder.itemView)
        binding.apply {
            tvName.text = streamer.name
            tvCategory.text = streamer.category
            glide.load(streamer.image).into(ivStreamerImg)
        }

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(streamer)
                }
            }
        }

    }
}