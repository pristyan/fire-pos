package com.fire.pos.domain.productlist

import com.fire.core.model.Result
import com.fire.pos.model.view.Product


/**
 * Created by Chandra.
 **/

interface ProductListInteractor {

    suspend fun getProducts(): Result<List<Product>>

}