package com.fire.pos.model.view

import com.fire.pos.model.entity.ProductCartEntity
import com.fire.pos.model.entity.ProductEntity
import com.fire.pos.util.toIDR
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class ProductCart(

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

    val showQty: Boolean
        get() = qty > 0

    val formattedPrice: String
        get() = productPrice.toIDR()

    val formattedQty: String
        get() = "x$qty"

    val formattedSubtotal: String
        get() = (qty * productPrice).toIDR()

    val formattedStock: String
        get() = "/$productStock"

    constructor(entity: ProductEntity) : this(
        id = "",
        productId = entity.id.orEmpty(),
        productName = entity.name.orEmpty(),
        productPrice = entity.price ?: 0L,
        productImage = entity.image.orEmpty(),
        productStock = entity.stock ?: 0L,
        qty = 0
    )

    constructor(entity: ProductCartEntity?) : this(
        id = entity?.id.orEmpty(),
        productId = entity?.productId.orEmpty(),
        productName = entity?.productName.orEmpty(),
        productPrice = entity?.productPrice ?: 0L,
        productImage = entity?.productImage.orEmpty(),
        productStock = entity?.productStock,
        qty = entity?.qty ?: 0
    )

}