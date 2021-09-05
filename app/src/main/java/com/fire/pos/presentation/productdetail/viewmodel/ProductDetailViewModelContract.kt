package com.fire.pos.presentation.productdetail.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.fire.core.base.viewmodel.BaseViewModelContract
import com.fire.pos.model.view.Product
import kotlinx.coroutines.Job
import java.io.File


/**
 * Created by Chandra.
 **/

interface ProductDetailViewModelContract: BaseViewModelContract {

    val compressedImage: LiveData<File>

    val addProductSuccess: LiveData<Product>

    val updateProductSuccess: LiveData<Product>

    val deleteProductSuccess: LiveData<Boolean>

    val productError: LiveData<String>

    fun compressImage(context: Context, file: File): Job

    fun addProduct(name: String, price: Long, stock: Long): Job

    fun updateProduct(product: Product): Job

    fun deleteProduct(product: Product): Job
}