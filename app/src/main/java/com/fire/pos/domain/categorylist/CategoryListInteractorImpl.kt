package com.fire.pos.domain.categorylist

import com.fire.pos.data.repository.category.CategoryRepository
import com.fire.pos.model.entity.CategoryEntity
import com.fire.core.model.Result
import com.fire.pos.model.view.Category
import com.fire.core.util.FirestoreIdGenerator
import java.util.*
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CategoryListInteractorImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
): CategoryListInteractor {

    override suspend fun getCategoryList(): Result<List<Category>> {
        return when (val result = categoryRepository.getCategoryList()) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> {
                val list = result.data?.map { Category(it) } ?: emptyList()
                Result.Success(list)
            }
        }
    }

    override suspend fun insertCategory(name: String): Result<Boolean> {
        val request = CategoryEntity(
            id = FirestoreIdGenerator.generateCategoryId(),
            name = name,
            createdAt = Date()
        )
        return when (val result = categoryRepository.insertCategory(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }

    override suspend fun updateCategory(category: Category): Result<Boolean> {
        val request = CategoryEntity(category)
        return when (val result = categoryRepository.updateCategory(request)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }

    override suspend fun deleteCategory(id: String): Result<Boolean> {
        return when (val result = categoryRepository.deleteCategory(id)) {
            is Result.Error -> Result.Error(result.message)
            is Result.Success -> Result.Success(result.data ?: false)
        }
    }
}