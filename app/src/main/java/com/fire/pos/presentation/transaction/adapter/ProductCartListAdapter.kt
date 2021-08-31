package com.fire.pos.presentation.transaction.adapter

import com.bumptech.glide.Glide
import com.fire.pos.R
import com.fire.pos.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemProductCartBinding
import com.fire.pos.model.view.ProductCart
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductCartListAdapter @Inject constructor() :
    BaseRecyclerAdapter<ProductCart, ListItemProductCartBinding>() {

    var callback: Callback? = null

    interface Callback {
        fun onItemClick(item: ProductCart)
    }

    override fun getLayoutId(): Int {
        return R.layout.list_item_product_cart
    }

    override fun onBind(binding: ListItemProductCartBinding, position: Int, item: ProductCart) {
        binding.data = item
        Glide.with(binding.imgProduct.context).load(item.productImage).into(binding.imgProduct)
        binding.root.setOnClickListener {
            callback?.onItemClick(item)
        }
        binding.executePendingBindings()
    }

    fun update(item: ProductCart) {
        val index = getItems().indexOfFirst { it.productId == item.productId }
        updateItem(index, item)
    }
}