package com.fire.pos.model.entity

import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.model.view.Product
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


/**
 * Created by Chandra.
 **/

data class ProductEntity(
    @SerializedName(FirestoreConstant.FIELD_ID)
    var id: String?,

    @SerializedName(FirestoreConstant.FIELD_NAME)
    var name: String?,

    @SerializedName(FirestoreConstant.FIELD_PRICE)
    var price: Long?,

    @SerializedName(FirestoreConstant.FIELD_IMAGE)
    var image: String?,

    @SerializedName(FirestoreConstant.FIELD_CATEGORY_ID)
    var categoryId: String?,

    @SerializedName(FirestoreConstant.FIELD_SKU)
    var sku: String?,

    @SerializedName(FirestoreConstant.FIELD_STOCK)
    var stock: Long?,

    @SerializedName(FirestoreConstant.FIELD_IMAGE_FILE_NAME)
    var imageFileName: String?,

    @SerializedName(FirestoreConstant.FIELD_CREATED_AT)
    val createdAt: Date?

) : Serializable {

    constructor(data: DocumentSnapshot?) : this(
        id = data?.id,
        name = data?.getString(FirestoreConstant.FIELD_NAME).orEmpty(),
        price = data?.getLong(FirestoreConstant.FIELD_PRICE) ?: 0L,
        image = data?.getString(FirestoreConstant.FIELD_IMAGE).orEmpty(),
        categoryId = data?.getString(FirestoreConstant.FIELD_CATEGORY_ID).orEmpty(),
        sku = data?.getString(FirestoreConstant.FIELD_SKU).orEmpty(),
        stock = data?.getLong(FirestoreConstant.FIELD_STOCK) ?: 0L,
        imageFileName = data?.getString(FirestoreConstant.FIELD_IMAGE_FILE_NAME).orEmpty(),
        createdAt = data?.getTimestamp(FirestoreConstant.FIELD_CREATED_AT)?.toDate() ?: Date()
    )

    constructor(data: Product) : this(
        id = data.id,
        name = data.name,
        price = data.price,
        image = data.image,
        categoryId = data.categoryId,
        sku = data.sku,
        stock = data.stock,
        imageFileName = data.imageFileName,
        createdAt = data.createdAt
    )

}