package com.fire.pos.domain.interactor.productdetail

import com.fire.pos.data.constant.ResponseConstant
import com.fire.pos.data.response.Result
import com.fire.pos.data.view.Product
import com.fire.pos.domain.repository.remote.ProductRemoteDataSource
import java.io.File
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class ProductDetailInteractorImpl @Inject constructor(
    private val productDetailDataSource: ProductRemoteDataSource
) : ProductDetailInteractor {

    override suspend fun addProduct(
        name: String,
        price: Long,
        stock: Long,
        image: File
    ): Result<Product> {
        val imageResponse = productDetailDataSource.uploadImage(image)

        if (imageResponse.isSuccess) {
            val productResponse = productDetailDataSource.addProduct(
                name = name,
                price = price,
                stock = stock,
                image = imageResponse.data?.toString().orEmpty(),
                imageFileName = image.name
            )
            return if (productResponse.isSuccess) {
                val newProduct = Product(
                    id = productResponse.data?.id.orEmpty(),
                    name = name,
                    price = price,
                    stock = stock,
                    image = imageResponse.data?.toString().orEmpty(),
                    imageFileName = image.name
                )
                Result.Success(newProduct)
            } else {
                Result.Error(
                    productResponse.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG
                )
            }
        } else {
            return Result.Error(
                imageResponse.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG
            )
        }
    }

    override suspend fun updateProduct(
        product: Product,
        newImage: File?,
    ): Result<Product> {
        if (newImage == null) {

            // update product without uploading new image
            val response = productDetailDataSource.updateProduct(
                product.id,
                product.name,
                product.price,
                product.stock,
                product.image,
                product.imageFileName
            )
            return if (response.isSuccess) {
                Result.Success(product)
            } else {
                Result.Error(response.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG)
            }
        } else {

            // update product with new image
            val imageResponse = productDetailDataSource.uploadImage(newImage)

            if (imageResponse.isSuccess) {

                // delete previous image
                productDetailDataSource.deleteImage(product.imageFileName)

                // save data to firestore
                val productResponse = productDetailDataSource.updateProduct(
                    product.id,
                    product.name,
                    product.price,
                    product.stock,
                    imageResponse.data?.toString().orEmpty(),
                    newImage.name
                )

                return if (productResponse.isSuccess) {
                    val newProduct = product.copy(image = imageResponse.data?.toString().orEmpty())
                    Result.Success(newProduct)
                } else {
                    Result.Error(
                        productResponse.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG
                    )
                }
            } else {
                return Result.Error(
                    imageResponse.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG
                )
            }
        }
    }

    override suspend fun deleteProduct(product: Product): Result<Boolean> {

        // delete data from firestore
        val response = productDetailDataSource.deleteProduct(product.id)
        return if (response.isSuccess) {

            // delete image from storage
            productDetailDataSource.deleteImage(product.imageFileName)

            Result.Success(true)
        } else {
            Result.Error(response.throwable?.message ?: ResponseConstant.SOMETHING_GOES_WRONG)
        }
    }


}