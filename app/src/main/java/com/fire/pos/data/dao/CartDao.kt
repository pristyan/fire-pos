package com.fire.pos.data.dao

import androidx.room.*
import com.fire.pos.constant.AppConstant
import com.fire.pos.model.db.ProductCartDbEntity


/**
 * Created by Chandra.
 **/

@Dao
interface CartDao {

    @Insert(entity = ProductCartDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productCartDbEntity: ProductCartDbEntity)

    @Delete(entity = ProductCartDbEntity::class)
    suspend fun delete(productCartDbEntity: ProductCartDbEntity)

    @Update(entity = ProductCartDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(productCartDbEntity: ProductCartDbEntity)

    @Query(value = "select * from ${AppConstant.TABLE_CART}")
    suspend fun getAll(): List<ProductCartDbEntity>

    @Query("delete from ${AppConstant.TABLE_CART}")
    suspend fun clearCart()

}