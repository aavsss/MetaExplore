package com.aavashsthapit.myapplication.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.aavashsthapit.myapplication.R
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject

class StreamersAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseStreamersAdapter(R.layout.list_item){

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: StreamerViewHolder, position: Int) {
        val streamer = streamers[position]
        holder.itemView.apply {
            tv_name.text = streamer.name
            tv_category.text = streamer.category
            glide.load(streamer.image).into(iv_streamer_img)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(streamer)
                }
            }
        }
    }
}