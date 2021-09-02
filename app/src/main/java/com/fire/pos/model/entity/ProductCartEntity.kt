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

    @SerializedName("id")
    var id: String,

    @SerializedName("product_id")
    val productId: String,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("product_price")
    val productPrice: Long,

    @SerializedName("product_image")
    val productImage: String,

    @SerializedName("product_stock")
    var productStock: Long?,

    @SerializedName("qty")
    var qty: Int

) : Serializable {

    constructor(id: String, data: DocumentSnapshot?) : this(
        id = "",
        productId = id,
        productName = data?.get("name")?.toString().orEmpty(),
        productPrice = data?.get("price")?.toString()?.toLong() ?: 0L,
        productImage = data?.get("image")?.toString().orEmpty(),
        productStock = data?.get("stock")?.toString()?.toLong() ?: 0L,
        qty = 0
    )

    constructor(entity: ProductCartDbEntity?) : this(
        id = entity?.id.orEmpty(),
        productId = entity?.productId.orEmpty(),
        productName = entity?.productName.orEmpty(),
        productPrice = entity?.productPrice ?: 0L,
        productImage = entity?.productImage.orEmpty(),
        productStock = entity?.productStock,
        qty = entity?.qty ?: 0
    )

    constructor(data: ProductCart) : this(
        id = data.id,
        productId = data.productId,
        productName = data.productName,
        productPrice = data.productPrice,
        productImage = data.productImage,
        productStock = data.productStock,
        qty = data.qty
    )

    constructor(data: DocumentSnapshot?) : this(
        id = data?.id.orEmpty(),
        productId = data?.getString(FirestoreConstant.FIELD_CART_PRODUCT_ID).orEmpty(),
        productName = data?.getString(FirestoreConstant.FIELD_CART_PRODUCT_NAME).orEmpty(),
        productPrice = data?.getLong(FirestoreConstant.FIELD_CART_PRODUCT_PRICE) ?: 0L,
        productImage = data?.getString(FirestoreConstant.FIELD_CART_PRODUCT_IMAGE).orEmpty(),
        productStock = null,
        qty = (data?.get(FirestoreConstant.FIELD_CART_QTY)?.toString() ?: "0").toInt()
    )

}