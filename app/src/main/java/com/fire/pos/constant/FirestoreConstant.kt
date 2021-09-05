package com.fire.pos.constant


/**
 * Created by Chandra.
 **/
object FirestoreConstant {

    // collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_PRODUCTS = "products"
    const val COLLECTION_STORES = "store"
    const val COLLECTION_CATEGORIES = "categories"
    const val COLLECTION_TRANSACTIONS = "transactions"
    const val COLLECTION_TRANSACTION_ITEMS = "items"

    // fields
    const val FIELD_ID = "id"
    const val FIELD_NAME = "name"
    const val FIELD_ADDRESS = "address"
    const val FIELD_PHONE = "phone"
    const val FIELD_PRICE = "price"
    const val FIELD_STOCK = "stock"
    const val FIELD_IMAGE = "image"
    const val FIELD_SKU = "sku"
    const val FIELD_IMAGE_FILE_NAME = "image_file_name"
    const val FIELD_CREATED_AT = "created_at"
    const val FIELD_QTY = "qty"
    const val FIELD_PAYMENT_METHOD = "payment_method"
    const val FIELD_TOTAL = "total"

    const val FIELD_CATEGORY_ID = "category_id"
    const val FIELD_TRANSACTION_ID = "id"
    const val FIELD_CART_ID = "cart_id"

    const val FIELD_PRODUCT_ID = "product_id"
    const val FIELD_PRODUCT_NAME = "product_name"
    const val FIELD_PRODUCT_PRICE = "product_price"
    const val FIELD_PRODUCT_IMAGE = "product_image"
    const val FIELD_PRODUCT_SKU = "product_sku"
    const val FIELD_PRODUCT_CATEGORY_ID = "product_category_id"

}