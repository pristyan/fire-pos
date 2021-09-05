package com.fire.pos.model.view

import com.fire.pos.model.entity.TransactionEntity
import com.fire.core.util.getStringDateTime
import com.fire.core.util.normalizeDate
import com.fire.core.util.toIDR
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class Transaction(
    @SerializedName("id")
    val id: String,

    @SerializedName("total")
    val total: Long,

    @SerializedName("items")
    val items: List<ProductCart>,

    @SerializedName("created_at")
    val createdAt: String
) : Serializable {

    constructor(entity: TransactionEntity?) : this(
        id = entity?.id.orEmpty(),
        total = entity?.total ?: 0L,
        createdAt = entity?.createdAt?.toDate()?.getStringDateTime().orEmpty(),
        items = entity?.items?.map { ProductCart(it) } ?: emptyList()
    )

    val formattedTotal: String
        get() = total.toIDR()

    val normalizedDate: String
        get() = createdAt.normalizeDate()
}