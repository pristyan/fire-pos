package com.fire.pos.presentation.productdetail


/**
 * Created by Chandra.
 **/

interface ProductDetailView {

    fun initView()

    fun initEasyImage()

    fun requestCameraPermission()

    fun requestReadStoragePermission()

    fun requestWriteStoragePermission()

    fun showImageSourceOptionDialog()

    fun addProduct()

    fun deleteProduct()

    fun updateProduct()

}