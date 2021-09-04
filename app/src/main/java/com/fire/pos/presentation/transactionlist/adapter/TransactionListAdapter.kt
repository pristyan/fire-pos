package com.fire.pos.presentation.transactionlist.adapter

import com.fire.pos.R
import com.fire.pos.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemTransactionBinding
import com.fire.pos.model.view.Transaction
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionListAdapter @Inject constructor() :
    BaseRecyclerAdapter<Transaction, ListItemTransactionBinding>() {

    var callback: Callback? = null

    interface Callback {
        fun onItemClick(item: Transaction)
    }

    override fun getLayoutId(): Int {
        return R.layout.list_item_transaction
    }

    override fun onBind(binding: ListItemTransactionBinding, position: Int, item: Transaction) {
        binding.data = item
        binding.root.setOnClickListener { callback?.onItemClick(item) }
        binding.executePendingBindings()
    }
}