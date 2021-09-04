package com.fire.pos.model.entity

import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.model.view.Category
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


/**
 * Created by Chandra.
 **/
data class CategoryEntity(

    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("created_at")
    val createdAt: Date

): Serializable {

    constructor(data: DocumentSnapshot?): this(
        id = data?.getString(FirestoreConstant.FIELD_ID),
        name = data?.getString(FirestoreConstant.FIELD_NAME),
        createdAt = data?.getTimestamp(FirestoreConstant.FIELD_CREATED_AT)?.toDate() ?: Date()
    )

    constructor(data: Category): this(
        id = data.id,
        name = data.name,
        createdAt = data.createdAt
    )
}
