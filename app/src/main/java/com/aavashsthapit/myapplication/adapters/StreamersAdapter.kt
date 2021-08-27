package com.aavashsthapit.myapplication.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.aavashsthapit.myapplication.R
import com.bumptech.glide.RequestManager
import javax.inject.Inject

/**
 * Adapter class
 * Injected Glide using DaggerHilt
 * differ instantiated here
 * ViewBinding to populate fields such as name and category
 * setting on OnClickListener so that it will send the streamer, the user clicked
 */
class StreamersAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseStreamersAdapter(R.layout.list_item) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: StreamerViewHolder, position: Int) {
        val streamer = streamerViewModels[position]
        binding.apply {
            viewmodel = streamerViewModels[position]
        }

        holder.itemView.apply {

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(streamer) // Passing streamer here
                }
            }

            setOnLongClickListener {
                onItemLongClickListener(streamer)
            }
        }
    }
}

