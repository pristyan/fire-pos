package com.fire.pos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fire.pos.constant.AppConstant
import com.fire.pos.data.dao.CartDao
import com.fire.pos.model.db.ProductCartDbEntity

/**
 * Created by Chandra.
 **/

@Database(
    entities = [ProductCartDbEntity::class],
    version = AppConstant.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

}