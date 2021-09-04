package com.fire.pos.util

import kotlin.random.Random


/**
 * Created by Chandra.
 **/
object FirestoreIdGenerator {

    private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun generateNumbers(): String {
        return Random.nextInt(9999).toString().padStart(4, '0')
    }

    private fun generateWords(): String {
        return StringBuilder()
            .append(ALPHABET[Random.nextInt(ALPHABET.length)])
            .append(ALPHABET[Random.nextInt(ALPHABET.length)])
            .append(ALPHABET[Random.nextInt(ALPHABET.length)])
            .append(ALPHABET[Random.nextInt(ALPHABET.length)])
            .toString()
    }

    fun generateProductId(): String {
        return "PRD-${generateNumbers()}-${generateWords()}-${generateNumbers()}"
    }

    fun generateTransactionId(): String {
        return "TRX-${generateNumbers()}-${generateWords()}-${generateNumbers()}"
    }

    fun generateStoreId(): String {
        return "STR-${generateNumbers()}-${generateWords()}-${generateNumbers()}"
    }
}