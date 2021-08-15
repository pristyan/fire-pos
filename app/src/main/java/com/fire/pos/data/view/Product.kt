package com.fire.pos.data.view

import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import java.io.Serializable


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

    @SerializedName("stock")
    val stock: Long = 0,

    @SerializedName("image_file_name")
    val imageFileName: String
): Serializable {

    @SerializedName("qty")
    var qty: Long? = null

    val stringPrice: String
        get() = price.toString()

    val stringStock: String
        get() = stock.toString()

    constructor(id: String, data: DocumentSnapshot): this(
        id = id,
        name = data["name"]?.toString().orEmpty(),
        price = data["price"]?.toString()?.toLong() ?: 0L,
        image = data["image"]?.toString().orEmpty(),
        stock = data["stock"]?.toString()?.toLong() ?: 0L,
        imageFileName = data["image_file_name"]?.toString().orEmpty()
    )

}