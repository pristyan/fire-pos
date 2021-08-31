package com.fire.pos.model.entity

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

}