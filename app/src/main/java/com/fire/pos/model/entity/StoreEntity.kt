package com.fire.pos.model.entity

import com.fire.pos.constant.FirestoreConstant
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/
data class StoreEntity(

    @SerializedName(FirestoreConstant.FIELD_ID)
    val id: String? = null,

    @SerializedName(FirestoreConstant.FIELD_NAME)
    val name: String? = null,

    @SerializedName(FirestoreConstant.FIELD_ADDRESS)
    val address: String? = null,

    @SerializedName(FirestoreConstant.FIELD_PHONE)
    val phone: String? = null

): Serializable
