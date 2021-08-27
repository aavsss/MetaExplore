package com.aavashsthapit.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aavashsthapit.myapplication.data.entity.StreamerViewModel
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

    protected val diffCallback = object : DiffUtil.ItemCallback<StreamerViewModel>() {
        override fun areItemsTheSame(oldItem: StreamerViewModel, newItem: StreamerViewModel): Boolean {
            return oldItem.display_name == newItem.display_name
        }

        override fun areContentsTheSame(oldItem: StreamerViewModel, newItem: StreamerViewModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ: AsyncListDiffer<StreamerViewModel>
    protected lateinit var binding: ListItemBinding
    var streamerViewModels: List<StreamerViewModel>
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

    protected var onItemClickListener: ((StreamerViewModel) -> Unit)? = null
    protected lateinit var onItemLongClickListener: ((StreamerViewModel) -> Boolean)

    fun setItemClickListener(listener: (StreamerViewModel) -> Unit) {
        onItemClickListener = listener
    }

    fun setItemLongClickListener(listener: (StreamerViewModel) -> Boolean) {
        onItemLongClickListener = listener
    }

    override fun getItemCount(): Int {
        return streamerViewModels.size
    }
}
