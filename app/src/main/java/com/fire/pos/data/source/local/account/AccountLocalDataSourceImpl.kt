package com.fire.pos.data.source.local.account

import android.content.SharedPreferences
import com.fire.pos.model.entity.UserEntity
import com.google.gson.Gson
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class AccountLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
): AccountLocalDataSource {

    companion object {
        private const val USER = "user"
    }

    override fun setUser(data: UserEntity) {
        sharedPreferences.edit().putString(USER, gson.toJson(data)).apply()
    }

    override fun getUser(): UserEntity? {
        val json = sharedPreferences.getString(USER, null)
        return try {
            gson.fromJson(json, UserEntity::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun deleteUser() {
        sharedPreferences.edit().remove(USER).apply()
    }

    override fun isLoggedIn(): Boolean {
        return getUser() != null
    }
}