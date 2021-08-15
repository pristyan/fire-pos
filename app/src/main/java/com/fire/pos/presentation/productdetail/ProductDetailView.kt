package com.fire.pos.presentation.productdetail

/**
 * Created by Chandra.
 **/

interface ProductDetailView {

    fun initView()

    fun initEasyImage()

    fun requestPermission(permission: String)

    fun showImageSourceOptionDialog()

    fun addProduct()

    fun deleteProduct()

    fun updateProduct()

}