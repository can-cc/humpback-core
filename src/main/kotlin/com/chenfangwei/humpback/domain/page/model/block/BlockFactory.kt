package com.chenfangwei.humpback.domain.page.model.block

import com.chenfangwei.humpback.share.factory.generateId
import org.springframework.stereotype.Service

@Service
class BlockFactory {
    fun createBlock(type: BlockType, content: String): PageBlock {
        val id = generateId()
        return when (type) {
            BlockType.Html -> HtmlBlock(id, content)
            BlockType.Image -> ImageBlock(id, content)
        }
    }
}