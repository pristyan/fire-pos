package com.fire.pos.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/
data class ErrorResponse(

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String

): Serializable
