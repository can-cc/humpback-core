package com.chenfangwei.humpback.domain.page.command

import com.chenfangwei.humpback.domain.page.model.block.BlockType

data class CreatePageBlockCommand(
        val spaceId: String,
        val pageId: String,
        var content: String
) {
    lateinit var userId: String
    lateinit var blockType: BlockType
    var previousBlockId: String? = null

    constructor(spaceId: String,
                pageId: String,
                content: String, previousBlockId: String) : this(spaceId, pageId, content) {
        this.previousBlockId = previousBlockId
    }

}