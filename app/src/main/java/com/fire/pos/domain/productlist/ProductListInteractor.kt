package com.fire.pos.domain.productlist

import com.fire.pos.model.response.Result
import com.fire.pos.model.view.Product


/**
 * Created by Chandra.
 **/

interface ProductListInteractor {

    suspend fun getProducts(): Result<List<Product>>

}