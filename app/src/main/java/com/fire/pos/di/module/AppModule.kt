package com.fire.pos.di.module

import android.content.Context
import androidx.room.Room
import com.fire.pos.constant.AppConstant
import com.fire.pos.database.AppDatabase
import dagger.Module
import dagger.Provides


/**
 * Created by Chandra.
 **/

@Module(includes = [RepositoryModule::class, DataSourceModule::class])
object AppModule {

    @JvmStatic
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppConstant.DB_NAME
        ).build()
    }

}