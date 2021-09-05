package com.fire.pos.model.entity

import com.fire.pos.constant.FirestoreConstant
import com.fire.pos.model.db.ProductCartDbEntity
import com.fire.pos.model.view.ProductCart
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class ProductCartEntity(

    @SerializedName(FirestoreConstant.FIELD_ID)
    var id: String,

    @SerializedName(FirestoreConstant.FIELD_PRODUCT_ID)
    val productId: String,

    @SerializedName(FirestoreConstant.FIELD_PRODUCT_NAME)
    val productName: String,

    @SerializedName(FirestoreConstant.FIELD_PRODUCT_PRICE)
    val productPrice: Long,

    @SerializedName(FirestoreConstant.FIELD_PRODUCT_IMAGE)
    val productImage: String,

    @SerializedName(FirestoreConstant.FIELD_PRODUCT_CATEGORY_ID)
    val productCategoryId: String,

    @SerializedName(FirestoreConstant.FIELD_PRODUCT_SKU)
    val productSku: String,

    @SerializedName(FirestoreConstant.FIELD_STOCK)
    var productStock: Long?,

    @SerializedName(FirestoreConstant.FIELD_QTY)
    var qty: Int

) : Serializable {

    constructor(entity: ProductCartDbEntity?) : this(
        id = entity?.id.orEmpty(),
        productId = entity?.productId.orEmpty(),
        productName = entity?.productName.orEmpty(),
        productPrice = entity?.productPrice ?: 0L,
        productImage = entity?.productImage.orEmpty(),
        productCategoryId = entity?.productCategoryId.orEmpty(),
        productSku = entity?.productSku.orEmpty(),
        productStock = entity?.productStock,
        qty = entity?.qty ?: 0
    )

    constructor(data: ProductCart) : this(
        id = data.id,
        productId = data.productId,
        productName = data.productName,
        productPrice = data.productPrice,
        productImage = data.productImage,
        productCategoryId = data.productCategoryId,
        productSku = data.productSku,
        productStock = data.productStock,
        qty = data.qty
    )

    constructor(data: DocumentSnapshot?) : this(
        id = data?.id.orEmpty(),
        productId = data?.getString(FirestoreConstant.FIELD_PRODUCT_ID).orEmpty(),
        productName = data?.getString(FirestoreConstant.FIELD_PRODUCT_NAME).orEmpty(),
        productPrice = data?.getLong(FirestoreConstant.FIELD_PRODUCT_PRICE) ?: 0L,
        productImage = data?.getString(FirestoreConstant.FIELD_PRODUCT_IMAGE).orEmpty(),
        productCategoryId = data?.getString(FirestoreConstant.FIELD_PRODUCT_CATEGORY_ID).orEmpty(),
        productSku = data?.getString(FirestoreConstant.FIELD_PRODUCT_SKU).orEmpty(),
        productStock = null,
        qty = (data?.get(FirestoreConstant.FIELD_QTY)?.toString() ?: "0").toInt()
    )

}