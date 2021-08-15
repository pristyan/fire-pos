package com.fire.pos.data.view

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

    constructor(firebaseUser: FirebaseUser?) : this(
        uid = firebaseUser?.uid.orEmpty(),
        email = firebaseUser?.email.orEmpty()
    )

}