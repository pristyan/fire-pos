package com.fire.pos.data.dao

import androidx.room.*
import com.fire.pos.constant.AppConstant
import com.fire.pos.model.db.ProductDbEntity


/**
 * Created by Chandra.
 **/
@Dao
interface ProductDao {

    @Query("select * from ${AppConstant.TABLE_PRODUCT}")
    suspend fun getProducts(): List<ProductDbEntity>

    @Insert(entity = ProductDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(list: List<ProductDbEntity>)

    @Insert(entity = ProductDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productDbEntity: ProductDbEntity)

    @Update(entity = ProductDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(productDbEntity: ProductDbEntity)

    @Query("select * from ${AppConstant.TABLE_PRODUCT} where ${AppConstant.COL_PRODUCT_ID} == :id")
    suspend fun getProductById(id: String): List<ProductDbEntity>

    @Query("delete from ${AppConstant.TABLE_PRODUCT} where ${AppConstant.COL_PRODUCT_ID} == :id")
    suspend fun deleteProductById(id: String)

}