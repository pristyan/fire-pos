package com.fire.pos.domain.payment

import com.fire.pos.constant.AppConstant
import com.fire.pos.data.repository.product.ProductRepository
import com.fire.pos.data.repository.transaction.TransactionRepository
import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.entity.TransactionEntity
import com.fire.pos.model.response.Result
import com.fire.pos.model.view.ProductCart
import com.fire.pos.util.getCurrentDateTime
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class PaymentInteractorImpl @Inject constructor(
    private val productRepository: ProductRepository,
    private val transactionRepository: TransactionRepository
) : PaymentInteractor {

    override suspend fun getCartList(): Result<List<ProductCart>> {
        return when (val result = productRepository.getCart()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map { ProductCart(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

    override suspend fun pay(items: List<ProductCart>): Result<Boolean> {
        val cartList = items.map { ProductCartEntity(it) }
        val request = TransactionEntity(
            id = null,
            items = cartList,
            paymentMethod = AppConstant.PAYMENT_METHOD_CASH,
            total = items.sumOf { it.qty * it.productPrice },
            createdAt = getCurrentDateTime()
        )

        // create transaction
        return when (val result = transactionRepository.createTransaction(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {

                // update product stock
                when (val sResult = productRepository.updateProductStock(cartList)) {
                    is Result.Error -> Result.Error(sResult.message)
                    is Result.Success -> {

                        // clear cart
                        when (val cResult = productRepository.clearCart()) {
                            is Result.Error -> Result.Error(cResult.message)
                            is Result.Success -> Result.Success(cResult.data ?: false)
                        }
                    }
                }
            }
        }
    }

}