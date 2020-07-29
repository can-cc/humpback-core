package com.chenfangwei.humpback.domain.page.model

import com.chenfangwei.humpback.domain.page.exception.InvalidPageOperationException
import com.chenfangwei.humpback.domain.page.model.block.HtmlBlock
import com.chenfangwei.humpback.share.factory.generateId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document(collection = "page")
class Page(val creatorId: String, val spaceId: String) {
    @Id
    var id: String? = null

    var title: String? = null

    var blocks: ArrayList<PageBlock>? = null

    @CreatedDate
    private val createdDate: Date? = null

    fun addBlock(block: PageBlock, previousBlockId: String?) {
        if (previousBlockId != null) {
            val lastBlockIndex = blocks!!.indexOfFirst { b -> b.id == previousBlockId }
            if (lastBlockIndex < 0) {
                throw InvalidPageOperationException("Previous block not found")
            }
            if (blocks == null) {
                blocks = arrayListOf(block)
                return
            }
            blocks!!.add(lastBlockIndex + 1, block)
        } else {
            blocks!!.add(block)
        }
    }

    fun updateBlock(blockId: String, content: String) {
        val block = findBlock(blockId) ?: throw InvalidPageOperationException("block not found")
        block.content = content
    }

    fun resortBlocks(blockIds: ArrayList<String>) {
        if (this.blocks == null) {
            throw InvalidPageOperationException("page blocks is null")
        }
        this.blocks!!.sortWith(Comparator<PageBlock> { o1, o2 -> blockIds.indexOf(o1.id) - blockIds.indexOf(o2.id) })
    }

    private fun findBlock(blockId: String): PageBlock? {
        if (blocks == null) {
            return null
        }
        return blocks!!.find { b -> b.id == blockId }
    }
}