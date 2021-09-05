package com.fire.pos.presentation.transactiondetail.adapter

import com.fire.pos.R
import com.fire.core.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemTransactionItemBinding
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class TransactionItemAdapter @Inject constructor() :
    BaseRecyclerAdapter<ProductCart, ListItemTransactionItemBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.list_item_transaction_item
    }

    override fun onBind(binding: ListItemTransactionItemBinding, position: Int, item: ProductCart) {
        binding.data = item
        binding.executePendingBindings()
    }
}