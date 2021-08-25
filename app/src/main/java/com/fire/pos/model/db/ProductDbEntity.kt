package com.fire.pos.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fire.pos.constant.AppConstant
import com.fire.pos.model.entity.ProductEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Created by Chandra.
 **/
@Entity(tableName = AppConstant.TABLE_PRODUCT)
data class ProductDbEntity(

    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_NAME)
    @SerializedName("name")
    val name: String?,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_PRICE)
    @SerializedName("price")
    val price: Long?,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_IMAGE)
    @SerializedName("image")
    val image: String?,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_STOCK)
    @SerializedName("stock")
    val stock: Long?,

    @ColumnInfo(name = AppConstant.COL_PRODUCT_IMAGE_FILE_NAME)
    @SerializedName("image_file_name")
    val imageFileName: String?

): Serializable {

    constructor(entity: ProductEntity): this(
        entity.id.orEmpty(),
        entity.name,
        entity.price,
        entity.image,
        entity.stock,
        entity.imageFileName
    )

}