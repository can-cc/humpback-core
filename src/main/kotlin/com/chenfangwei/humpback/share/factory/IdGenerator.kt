package com.chenfangwei.humpback.share.factory

import java.util.*

fun generateId(): String {
    val uuid = UUID.randomUUID()
    return uuid.toString()
}