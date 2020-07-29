package com.chenfangwei.humpback.domain.page.model.block

import com.chenfangwei.humpback.domain.page.model.BlockType
import com.chenfangwei.humpback.domain.page.model.PageBlock

class ImageBlock(id: String, content: String): PageBlock(id, content) {
    final val type = BlockType.Image
}