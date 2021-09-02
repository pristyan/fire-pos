package com.fire.pos.presentation.productlist

import com.fire.pos.model.view.Product


/**
 * Created by Chandra.
 **/

interface ProductListView {

    fun initView()

    fun getProductList()

    fun searchProduct(keyword: String?): Boolean

    fun observeNavigation()

    fun navigateToProductDetail(product: Product)

    fun navigateToAddProduct()
}