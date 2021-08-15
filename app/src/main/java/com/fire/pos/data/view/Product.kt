package com.fire.pos.data.view

import com.google.firebase.firestore.DocumentReference
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
    val stock: Long = 0
): Serializable {

    @SerializedName("qty")
    var qty: Long? = null

    constructor(data: DocumentSnapshot): this(
        id = data["id"]?.toString().orEmpty(),
        name = data["name"]?.toString().orEmpty(),
        price = data["price"]?.toString()?.toLong() ?: 0L,
        image = data["image"]?.toString().orEmpty(),
        stock = data["stock"]?.toString()?.toLong() ?: 0L
    )

}