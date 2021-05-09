package com.aavashsthapit.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aavashsthapit.myapplication.data.entity.Streamer

/**
 * Class to be inherited
 * Handles diffCallback : Checks weather the elements in the list is same or not
 * Abstracted differ
 * Instantiated streamers here
 * Bare bones for onclick listener
 */
abstract class BaseStreamersAdapter(
    private val layoutId: Int
) : RecyclerView.Adapter<BaseStreamersAdapter.StreamerViewHolder>(){

    class StreamerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    protected val diffCallback = object : DiffUtil.ItemCallback<Streamer>() {
        override fun areItemsTheSame(oldItem: Streamer, newItem: Streamer): Boolean {
            return oldItem.display_name == newItem.display_name
        }

        override fun areContentsTheSame(oldItem: Streamer, newItem: Streamer): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ: AsyncListDiffer<Streamer>

    var streamers: List<Streamer>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamerViewHolder {
        return StreamerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )
    }

    protected var onItemClickListener: ((Streamer) -> Unit)? = null

    fun setItemClickListener(listener: (Streamer) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return streamers.size
    }
}