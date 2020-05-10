package com.chenfangwei.humpback.domain.page.command

data class CreatePageBlockCommand(
        val spaceId: String,
        val pageId: String,
        var content: String
) {
    lateinit var userId: String
    var previousBlockId: String? = null

    constructor(spaceId: String,
                pageId: String,
                content: String, previousBlockId: String) : this(spaceId, pageId, content) {
        this.previousBlockId = previousBlockId
    }

}