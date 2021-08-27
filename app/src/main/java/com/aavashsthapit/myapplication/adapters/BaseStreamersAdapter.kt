package com.aavashsthapit.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aavashsthapit.myapplication.data.entity.Streamer
import com.aavashsthapit.myapplication.databinding.ListItemBinding

/**
 * Class to be inherited
 * Handles diffCallback : Checks weather the elements in the list is same or not
 * Abstracted differ
 * Instantiated streamers here
 * Bare bones for onclick listener
 */
abstract class BaseStreamersAdapter(
    private val layoutId: Int
) : RecyclerView.Adapter<BaseStreamersAdapter.StreamerViewHolder>() {

    class StreamerViewHolder(itemView: ListItemBinding) : RecyclerView.ViewHolder(itemView.root)

    protected val diffCallback = object : DiffUtil.ItemCallback<Streamer>() {
        override fun areItemsTheSame(oldItem: Streamer, newItem: Streamer): Boolean {
            return oldItem.display_name == newItem.display_name
        }

        override fun areContentsTheSame(oldItem: Streamer, newItem: Streamer): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ: AsyncListDiffer<Streamer>
    protected lateinit var binding: ListItemBinding
    var streamers: List<Streamer>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return StreamerViewHolder(binding)
    }

    protected var onItemClickListener: ((Streamer) -> Unit)? = null
    protected lateinit var onItemLongClickListener: ((Streamer) -> Boolean)

    fun setItemClickListener(listener: (Streamer) -> Unit) {
        onItemClickListener = listener
    }

    fun setItemLongClickListener(listener: (Streamer) -> Boolean) {
        onItemLongClickListener = listener
    }

    override fun getItemCount(): Int {
        return streamers.size
    }
}
