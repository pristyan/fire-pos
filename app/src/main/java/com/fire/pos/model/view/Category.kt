package com.fire.pos.model.view

import com.fire.pos.model.entity.CategoryEntity
import com.fire.core.util.getStringDateTime
import com.fire.core.util.normalizeDate
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


/**
 * Created by Chandra.
 **/

data class Category(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("created_at")
    val createdAt: Date

): Serializable {

    constructor(entity: CategoryEntity?): this(
        id = entity?.id.orEmpty(),
        name = entity?.name.orEmpty(),
        createdAt = entity?.createdAt ?: Date()
    )

    val formattedDate: String
        get() = createdAt.getStringDateTime().normalizeDate()

}