package com.fire.pos.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class CartEntity(

    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val productId: String,

    @SerializedName("qty")
    val qty: Int

): Serializable