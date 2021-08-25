package com.fire.pos.model.view

import com.fire.pos.model.entity.UserEntity
import com.google.firebase.auth.FirebaseUser
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

data class User(
    @SerializedName("uid")
    val uid: String,

    @SerializedName("email")
    val email: String
) : Serializable {

    constructor(entity: UserEntity?) : this(
        uid = entity?.uid.orEmpty(),
        email = entity?.email.orEmpty()
    )

}