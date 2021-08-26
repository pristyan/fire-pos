package com.fire.pos.data.source.remote.account

import com.fire.pos.model.response.Result
import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.util.await
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountRemoteDataSourceImpl @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private var firebaseFirestore: FirebaseFirestore
) : AccountRemoteDataSource {

    override suspend fun getCurrentUser(): Result<FirebaseUser> {
        return when (val user = firebaseAuth.currentUser) {
            null -> Result.Error("User not logged in yet")
            else -> Result.Success(user)
        }
    }

    override suspend fun loginWithEmailPassword(
        email: String,
        password: String
    ): Result<AuthResult> {
        val task = firebaseAuth.signInWithEmailAndPassword(email, password)
        return task.await()
    }

    override suspend fun registerWithEmailPassword(
        email: String,
        password: String
    ): Result<AuthResult> {
        val task = firebaseAuth.createUserWithEmailAndPassword(email, password)
        return task.await()
    }

    override suspend fun registerToFirestore(
        uid: String,
        storeName: String
    ): Result<DocumentReference> {
        val data = mapOf(
            FirestoreConstant.FIELD_NAME to storeName,
            FirestoreConstant.FIELD_ADDRESS to "",
            FirestoreConstant.FIELD_PHONE to ""
        )
        val task = firebaseFirestore
            .collection(FirestoreConstant.COLLECTION_USERS)
            .document(uid)
            .collection(FirestoreConstant.COLLECTION_STORES)
            .add(data)

        return task.await()
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}