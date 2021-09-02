package com.fire.pos.presentation.history.adapter

import com.fire.pos.R
import com.fire.pos.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemHistoryBinding
import com.fire.pos.model.view.Transaction
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryAdapter @Inject constructor() :
    BaseRecyclerAdapter<Transaction, ListItemHistoryBinding>() {

    var callback: Callback? = null

    interface Callback {
        fun onItemClick(item: Transaction)
    }

    override fun getLayoutId(): Int {
        return R.layout.list_item_history
    }

    override fun onBind(binding: ListItemHistoryBinding, position: Int, item: Transaction) {
        binding.data = item
        binding.root.setOnClickListener { callback?.onItemClick(item) }
        binding.executePendingBindings()
    }
}