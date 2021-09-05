package com.fire.pos.presentation.productlist.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import com.bumptech.glide.Glide
import com.fire.pos.R
import com.fire.core.base.recyclerview.BaseRecyclerAdapter
import com.fire.pos.databinding.ListItemProductBinding
import com.fire.pos.model.view.Product
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductListAdapter @Inject constructor() : BaseRecyclerAdapter<Product, ListItemProductBinding>() {

    var callback: ProductListCallback? = null

    interface ProductListCallback {
        fun onItemClick(product: Product)
    }

    override fun getLayoutId(): Int {
        return R.layout.list_item_product
    }

    override fun onBind(binding: ListItemProductBinding, position: Int, item: Product) {
        binding.data = item
        Glide.with(binding.root).load(item.image).into(binding.imgProduct)

        val matrix = ColorMatrix()
        matrix.setSaturation(if (item.isOutOfStock) 0F else 1F)
        binding.imgProduct.colorFilter = ColorMatrixColorFilter(matrix)

        binding.root.setOnClickListener { callback?.onItemClick(item) }
    }

}