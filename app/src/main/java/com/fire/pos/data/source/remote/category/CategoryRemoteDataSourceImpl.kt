package com.fire.pos.data.source.remote.category

import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.model.entity.CategoryEntity
import com.fire.core.model.Result
import com.fire.core.util.await
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class CategoryRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : CategoryRemoteDataSource {

    private val categoryCollection: CollectionReference
        get() = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(firebaseAuth.uid as String)
            .collection(FirestoreConstant.COLLECTION_CATEGORIES)

    override suspend fun getCategoryList(): Result<QuerySnapshot> {
        val task = categoryCollection
            .orderBy(FirestoreConstant.FIELD_NAME, Query.Direction.ASCENDING)
            .get()
        return task.await()
    }

    override suspend fun insertCategory(entity: CategoryEntity): Result<Void> {
        val parameter = mapOf<String, Any>(
            FirestoreConstant.FIELD_ID to entity.id.orEmpty(),
            FirestoreConstant.FIELD_NAME to entity.name.orEmpty(),
            FirestoreConstant.FIELD_CREATED_AT to Timestamp(entity.createdAt)
        )
        return categoryCollection.document(entity.id.orEmpty()).set(parameter).await()
    }

    override suspend fun updateCategory(entity: CategoryEntity): Result<Void> {
        val parameter = mapOf<String, Any>(
            FirestoreConstant.FIELD_NAME to entity.name.orEmpty(),
            FirestoreConstant.FIELD_CREATED_AT to Timestamp(entity.createdAt)
        )
        return categoryCollection.document(entity.id.orEmpty()).update(parameter).await()
    }

    override suspend fun deleteCategory(id: String): Result<Void> {
        return categoryCollection.document(id).delete().await()
    }

}