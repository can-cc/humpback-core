package com.chenfangwei.humpback.space.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "space")
class Space(var name: String, var creatorId: String) {
    @Id
    var id: String? = null

    constructor(ID: String, name: String, creatorId: String) : this(name, creatorId) {
        this.id = ID
    }
}