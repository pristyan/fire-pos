package com.fire.pos.model.entity

import com.google.firebase.auth.AuthResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/
data class UserEntity(

    @SerializedName("uid")
    val uid: String?,

    @SerializedName("email")
    val email: String?

): Serializable {

    constructor(authResult: AuthResult?): this(
        authResult?.user?.uid,
        authResult?.user?.email
    )

}
