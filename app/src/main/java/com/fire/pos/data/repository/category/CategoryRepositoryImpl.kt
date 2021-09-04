package com.fire.pos.data.repository.category

import com.fire.pos.data.source.remote.category.CategoryRemoteDataSource
import com.fire.pos.model.entity.CategoryEntity
import com.fire.pos.model.response.Result
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getCategoryList(): Result<List<CategoryEntity>> {
        return when (val result = categoryRemoteDataSource.getCategoryList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.documents?.map { CategoryEntity(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

    override suspend fun insertCategory(entity: CategoryEntity): Result<Boolean> {
        return when (val result = categoryRemoteDataSource.insertCategory(entity)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(true)
        }
    }

    override suspend fun updateCategory(entity: CategoryEntity): Result<Boolean> {
        return when (val result = categoryRemoteDataSource.updateCategory(entity)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(true)
        }
    }

    override suspend fun deleteCategory(id: String): Result<Boolean> {
        return when (val result = categoryRemoteDataSource.deleteCategory(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(true)
        }
    }


}