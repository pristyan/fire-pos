package com.fire.pos.model.view

import com.fire.pos.model.entity.ProductEntity
import com.fire.core.util.toIDR
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


/**
 * Created by Chandra.
 **/

data class Product(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("image")
    val image: String,

    @SerializedName("category_id")
    val categoryId: String,

    @SerializedName("sku")
    val sku: String,

    @SerializedName("stock")
    val stock: Long = 0,

    @SerializedName("image_file_name")
    val imageFileName: String,

    @SerializedName("created_at")
    val createdAt: Date

) : Serializable {

    val stringPrice: String
        get() = price.toString()

    val stringStock: String
        get() = stock.toString()

    val formattedPrice: String
        get() = price.toIDR()

    val isOutOfStock: Boolean
        get() = stock == 0L

    constructor(entity: ProductEntity?) : this(
        id = entity?.id.orEmpty(),
        name = entity?.name.orEmpty(),
        price = entity?.price ?: 0L,
        image = entity?.image.orEmpty(),
        categoryId = entity?.categoryId.orEmpty(),
        sku = entity?.sku.orEmpty(),
        stock = entity?.stock ?: 0L,
        imageFileName = entity?.imageFileName.orEmpty(),
        createdAt = entity?.createdAt ?: Date()
    )

}