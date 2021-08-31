package com.fire.pos.model.entity

import com.fire.pos.model.view.Product
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class ProductEntity(
    @SerializedName("id")
    var id: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("price")
    var price: Long?,

    @SerializedName("image")
    var image: String?,

    @SerializedName("stock")
    var stock: Long?,

    @SerializedName("image_file_name")
    var imageFileName: String?
) : Serializable {

    constructor(id: String, data: DocumentSnapshot?) : this(
        id = id,
        name = data?.get("name")?.toString().orEmpty(),
        price = data?.get("price")?.toString()?.toLong() ?: 0L,
        image = data?.get("image")?.toString().orEmpty(),
        stock = data?.get("stock")?.toString()?.toLong() ?: 0L,
        imageFileName = data?.get("image_file_name")?.toString().orEmpty()
    )

    constructor(data: Product) : this(
        id = data.id,
        name = data.name,
        price = data.price,
        image = data.image,
        stock = data.stock,
        imageFileName = data.imageFileName
    )

}