package com.fire.pos.model.entity

import com.fire.pos.constant.FirestoreConstant
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class TransactionEntity(

    @SerializedName(FirestoreConstant.FIELD_ID)
    val id: String?,

    @SerializedName(FirestoreConstant.COLLECTION_TRANSACTION_ITEMS)
    val items: List<ProductCartEntity>?,

    @SerializedName(FirestoreConstant.FIELD_PAYMENT_METHOD)
    val paymentMethod: String?,

    @SerializedName(FirestoreConstant.FIELD_TOTAL)
    val total: Long?,

    @SerializedName(FirestoreConstant.FIELD_CREATED_AT)
    val createdAt: Timestamp?

): Serializable {

    constructor(data: DocumentSnapshot?): this(
        id = data?.id,
        paymentMethod = data?.getString(FirestoreConstant.FIELD_PAYMENT_METHOD).orEmpty(),
        total = data?.getLong(FirestoreConstant.FIELD_TOTAL) ?: 0L,
        createdAt = data?.getTimestamp(FirestoreConstant.FIELD_CREATED_AT),
        items = null
    )

    constructor(data: DocumentSnapshot?, items: List<ProductCartEntity>): this(
        id = data?.id,
        paymentMethod = data?.getString(FirestoreConstant.FIELD_PAYMENT_METHOD).orEmpty(),
        total = data?.getLong(FirestoreConstant.FIELD_TOTAL) ?: 0L,
        createdAt = data?.getTimestamp(FirestoreConstant.FIELD_CREATED_AT),
        items = items
    )

    val totalPrice: Long
        get() = items?.sumOf { it.qty * it.productPrice } ?: 0L
}