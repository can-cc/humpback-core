package com.chenfangwei.humpback.share.factory

import java.util.*

fun generateUUID(): String {
    val uuid = UUID.randomUUID()
    return uuid.toString()
}