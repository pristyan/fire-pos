package com.fire.pos.presentation.productdetail.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import com.fire.core.model.Result
import com.fire.pos.model.view.Product
import com.fire.pos.domain.productdetail.ProductDetailInteractor
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductDetailViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val productDetailInteractor: ProductDetailInteractor
) : BaseViewModel(), ProductDetailViewModelContract {

    private val _compressedImage = MutableLiveData<File>()
    override val compressedImage: LiveData<File> = _compressedImage

    private val _addProductSuccess = MutableLiveData<Product>()
    override val addProductSuccess: LiveData<Product> = _addProductSuccess

    private val _updateProductSuccess = MutableLiveData<Product>()
    override val updateProductSuccess: LiveData<Product> = _updateProductSuccess

    private val _deleteProductSuccess = MutableLiveData<Boolean>()
    override val deleteProductSuccess: LiveData<Boolean> = _deleteProductSuccess

    private val _productError = MutableLiveData<String>()
    override val productError: LiveData<String> = _productError

    override fun compressImage(context: Context, file: File): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            Compressor.compress(context, file) {
                default(width = 500, height = 500, quality = 60)
            }
        }
        _compressedImage.value = result
    }

    override fun addProduct(name: String, price: Long, stock: Long): Job =
        launch(schedulerProvider.ui()) {
            setLoading(true)

            val result = withContext(schedulerProvider.io()) {
                productDetailInteractor.addProduct(
                    name = name,
                    price = price,
                    stock = stock,
                    image = _compressedImage.value as File
                )
            }

            when (result) {
                is Result.Error -> _productError.value = result.message
                is Result.Success -> _addProductSuccess.value = result.data
            }

            setLoading(false)
        }

    override fun updateProduct(product: Product): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            productDetailInteractor.updateProduct(product, _compressedImage.value)
        }

        when (result) {
            is Result.Error -> _productError.value = result.message
            is Result.Success -> _updateProductSuccess.value = result.data
        }

        setLoading(false)
    }

    override fun deleteProduct(product: Product): Job = launch(schedulerProvider.ui()) {
        setLoading(true)

        val result = withContext(schedulerProvider.io()) {
            productDetailInteractor.deleteProduct(product)
        }

        when (result) {
            is Result.Error -> _productError.value = result.message
            is Result.Success -> _deleteProductSuccess.value = result.data
        }

        setLoading(false)
    }
}