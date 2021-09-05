package com.fire.core.util

import kotlin.random.Random


/**
 * Created by Chandra.
 **/

object FirestoreIdGenerator {

    private const val ALPHANUMERIC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun generateId(): String {
        return StringBuilder()
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .append(ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)])
            .toString()
    }

    fun generateProductId(): String {
        return "PRD-${generateId()}"
    }

    fun generateTransactionId(): String {
        return "TRX-${generateId()}"
    }

    fun generateStoreId(): String {
        return "STR-${generateId()}"
    }

    fun generateCategoryId(): String {
        return "CAT-${generateId()}"
    }
}