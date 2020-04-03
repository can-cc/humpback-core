package com.chenfangwei.humpback.space

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "space")
class Space(
        @Id
        var ID: String,
        var name: String
) {
    override fun toString(): String {
        val str = "{id= ${ID}, name = ${name}}"
        return str
    }
}