package com.chenfangwei.humpback.domain.page.model.block

import com.chenfangwei.humpback.domain.page.model.BlockType
import com.chenfangwei.humpback.domain.page.model.PageBlock
import com.chenfangwei.humpback.share.factory.generateId
import org.springframework.stereotype.Service

@Service
class BlockFactory {
    fun createBlock(type: BlockType, content: String): PageBlock {
        val id = generateId()
        val block = when (type) {
            BlockType.Html -> HtmlBlock(id, content)
            BlockType.Image -> HtmlBlock(id, content)
        }
        return block
    }
}