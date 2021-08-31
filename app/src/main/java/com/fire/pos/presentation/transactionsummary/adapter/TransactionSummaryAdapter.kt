package com.fire.pos.presentation.transactionsummary.adapter

import com.fire.pos.R
import com.fire.pos.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemSummaryBinding
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionSummaryAdapter @Inject constructor() :
    BaseRecyclerAdapter<ProductCart, ListItemSummaryBinding>() {

    var callback: Callback? = null

    override fun getLayoutId(): Int {
        return R.layout.list_item_summary
    }

    override fun onBind(binding: ListItemSummaryBinding, position: Int, item: ProductCart) {
        binding.data = item
        binding.btnPlus.setOnClickListener {
            item.qty++
            callback?.onPlus(item)
        }

        binding.btnMinus.setOnClickListener {
            if (item.qty == 1) callback?.onRemove(position, item)
            else {
                item.qty--
                callback?.onMinus(item)
            }
        }

        binding.executePendingBindings()
    }

    fun update(item: ProductCart) {
        val index = getItems().indexOfFirst { it.id == item.id }
        updateItem(index, item)
    }

    fun delete(item: ProductCart) {
        val index = getItems().indexOfFirst { it.id == item.id }
        deleteItem(index)
    }

    interface Callback {
        fun onPlus(item: ProductCart)
        fun onMinus(item: ProductCart)
        fun onRemove(position: Int, item: ProductCart)
    }

}