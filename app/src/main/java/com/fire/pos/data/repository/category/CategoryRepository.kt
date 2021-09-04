package com.fire.pos.data.repository.category

import com.fire.pos.model.entity.CategoryEntity
import com.fire.pos.model.response.Result


/**
 * Created by Chandra.
 **/

interface CategoryRepository {

    suspend fun getCategoryList(): Result<List<CategoryEntity>>

    suspend fun insertCategory(entity: CategoryEntity): Result<Boolean>

    suspend fun updateCategory(entity: CategoryEntity): Result<Boolean>

    suspend fun deleteCategory(id: String): Result<Boolean>

}