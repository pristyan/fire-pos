package com.fire.pos.data.source.remote.category

import com.fire.pos.model.entity.CategoryEntity
import com.fire.pos.model.response.Result
import com.google.firebase.firestore.QuerySnapshot


/**
 * Created by Chandra.
 **/

interface CategoryRemoteDataSource {

    suspend fun getCategoryList(): Result<QuerySnapshot>

    suspend fun insertCategory(entity: CategoryEntity): Result<Void>

    suspend fun updateCategory(entity: CategoryEntity): Result<Void>

    suspend fun deleteCategory(id: String): Result<Void>

}