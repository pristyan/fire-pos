package com.fire.pos.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class TransactionEntity(

    @SerializedName("id")
    val id: String?,

    @SerializedName("items")
    val items: List<ProductCartEntity>?,

    @SerializedName("payment_method")
    val paymentMethod: String?,

    @SerializedName("total")
    val total: Long?,

    @SerializedName("created_at")
    val createdAt: String?

): Serializable {

    val totalPrice: Long
        get() = items?.sumOf { it.qty * it.productPrice } ?: 0L

}