package com.fire.pos.domain.categorylist

import com.fire.core.model.Result
import com.fire.pos.model.view.Category


/**
 * Created by Chandra.
 **/

interface CategoryListInteractor {

    suspend fun getCategoryList(): Result<List<Category>>

    suspend fun insertCategory(name: String): Result<Boolean>

    suspend fun updateCategory(category: Category): Result<Boolean>

    suspend fun deleteCategory(id: String): Result<Boolean>

}