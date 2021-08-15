package com.fire.pos.domain.interactor.productlist

import com.fire.pos.data.response.Result
import com.fire.pos.data.view.Product


/**
 * Created by Chandra.
 **/

interface ProductListInteractor {

    suspend fun getProducts(): Result<List<Product>>

}