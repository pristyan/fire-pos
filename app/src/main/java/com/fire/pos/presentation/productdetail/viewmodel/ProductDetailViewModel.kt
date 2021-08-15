package com.fire.pos.presentation.productdetail.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fire.pos.base.viewmodel.BaseViewModel
import com.fire.pos.data.response.Result
import com.fire.pos.data.view.Product
import com.fire.pos.domain.interactor.productdetail.ProductDetailInteractor
import com.fire.pos.scheduler.SchedulerProvider
import id.zelory.compressor.Compressor
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

    private val _productError = MutableLiveData<String>()
    override val productError: LiveData<String> = _productError

    override fun compressImage(context: Context, file: File): Job = launch(schedulerProvider.ui()) {
        val result = withContext(schedulerProvider.io()) {
            Compressor.compress(context, file)
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
}