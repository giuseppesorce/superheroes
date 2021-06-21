package com.giuseppesorce.vodafone.commons.encrypt

import java.security.MessageDigest

import java.util.*

/**
 * @author Giuseppe Sorce
 */
fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}