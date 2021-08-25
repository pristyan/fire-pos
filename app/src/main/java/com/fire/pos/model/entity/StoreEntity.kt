package com.fire.pos.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/
data class StoreEntity(

    @SerializedName("uid")
    val uid: String?,

    @SerializedName("store_name")
    val storeName: String?

): Serializable
