package com.fire.pos.data.source.local.account

import com.fire.pos.model.entity.UserEntity


/**
 * Created by Chandra.
 **/

interface AccountLocalDataSource {

    fun setUser(data: UserEntity)

    fun getUser(): UserEntity?

    fun deleteUser()

    fun isLoggedIn(): Boolean

}