package com.aavashsthapit.myapplication.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.databinding.ListItemBinding
import com.bumptech.glide.RequestManager
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
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
) : BaseStreamersAdapter(R.layout.list_item){

    override val differ = AsyncListDiffer(this, diffCallback)
    lateinit var binding: ListItemBinding

    override fun onBindViewHolder(holder: StreamerViewHolder, position: Int) {
        val streamer = streamers[position]
        binding = ListItemBinding.bind(holder.itemView)
        binding.apply {
            tvName.text = streamer.display_name
            tvCategory.text = streamer.game_name
            tvIsLive.isVisible = streamer.is_live
            glide.load(streamer.thumbnail_url).into(ivStreamerImg)
            tvCastLang.text = "Language: ${streamer.broadcaster_language}"
            tvStartDate.text = "Start time: ${convertISOTime(streamer.started_at)}"
            clExtraInfo.visibility = if (streamer.expanded) View.VISIBLE else View.GONE
        }

        holder.itemView.apply {

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(streamer) //Passing streamer here
                }
            }

            setOnLongClickListener{
                onItemLongClickListener(streamer)
            }
        }

    }

    private fun convertISOTime(dateString: String) : String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = try {
            format.parse(dateString)
        }catch (e : Exception) {
            println(e)
            format.parse("2021-21-05T09:27:37Z")
        }
        return date!!.toString()
    }
}