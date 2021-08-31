package com.fire.pos.base.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Chandra.
 **/

abstract class BaseRecyclerAdapter<T, V : ViewDataBinding>: RecyclerView.Adapter<BaseViewHolder<V>>() {

    private var items: ArrayList<T> = arrayListOf()

    abstract fun getLayoutId(): Int

    abstract fun onBind(binding: V, position: Int, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayoutId(), parent, false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        val item = items[position]
        onBind(holder.binding, position, item)
        holder.binding.executePendingBindings()
    }

    fun getItems(): List<T> = items

    fun setItems(items: List<T>) {
        val size = itemCount
        this.items.clear()
        notifyItemRangeRemoved(0, size)

        this.items.addAll(items)
        notifyItemRangeInserted(0, items.size)
    }

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyItemRangeInserted(itemCount, items.size)
    }

    fun updateItem(index: Int, item: T) {
        items[index] = item
        notifyItemChanged(index)
    }

    fun deleteItem(index: Int) {
        notifyItemRemoved(index)
        items.removeAt(index)
    }

    fun clearItems() {
        val size = itemCount
        this.items = arrayListOf()
        notifyItemRangeRemoved(0, size)
    }

}