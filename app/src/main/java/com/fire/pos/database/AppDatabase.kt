package com.fire.pos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fire.pos.constant.AppConstant
import com.fire.pos.data.dao.ProductDao
import com.fire.pos.model.db.ProductDbEntity

/**
 * Created by Chandra.
 **/

@Database(
    entities = [ProductDbEntity::class],
    version = AppConstant.DB_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}