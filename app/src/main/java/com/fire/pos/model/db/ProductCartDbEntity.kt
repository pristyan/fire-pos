package com.fire.pos.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fire.pos.constant.AppConstant
import com.fire.pos.model.entity.ProductCartEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/

@Entity(tableName = AppConstant.TABLE_CART)
data class ProductCartDbEntity(

    @PrimaryKey
    @ColumnInfo(name = AppConstant.COL_ID)
    @SerializedName(AppConstant.COL_ID)
    var id: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_ID)
    @SerializedName(AppConstant.COL_PRODUCT_ID)
    val productId: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_NAME)
    @SerializedName(AppConstant.COL_PRODUCT_NAME)
    val productName: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_PRICE)
    @SerializedName(AppConstant.COL_PRODUCT_PRICE)
    val productPrice: Long,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_IMAGE)
    @SerializedName(AppConstant.COL_PRODUCT_IMAGE)
    val productImage: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_CATEGORY_ID)
    @SerializedName(AppConstant.COL_PRODUCT_CATEGORY_ID)
    val productCategoryId: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_SKU)
    @SerializedName(AppConstant.COL_PRODUCT_SKU)
    val productSku: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_STOCK)
    @SerializedName(AppConstant.COL_PRODUCT_STOCK)
    val productStock: Long,

    @ColumnInfo(name = AppConstant.COL_QTY)
    @SerializedName(AppConstant.COL_QTY)
    val qty: Int

): Serializable {

    constructor(entity: ProductCartEntity): this(
        id = entity.id,
        productId = entity.productId,
        productName = entity.productName,
        productPrice = entity.productPrice,
        productImage = entity.productImage,
        productCategoryId = entity.productCategoryId,
        productSku = entity.productSku,
        productStock = entity.productStock ?: 0L,
        qty = entity.qty
    )

}