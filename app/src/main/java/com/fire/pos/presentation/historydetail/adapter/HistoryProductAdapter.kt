package com.fire.pos.presentation.historydetail.adapter

import com.fire.pos.R
import com.fire.pos.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemHistoryProductBinding
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HistoryProductAdapter @Inject constructor() :
    BaseRecyclerAdapter<ProductCart, ListItemHistoryProductBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.list_item_history_product
    }

    override fun onBind(binding: ListItemHistoryProductBinding, position: Int, item: ProductCart) {
        binding.data = item
        binding.executePendingBindings()
    }
}