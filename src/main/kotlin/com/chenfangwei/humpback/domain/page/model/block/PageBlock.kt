package com.chenfangwei.humpback.domain.page.model.block

import com.chenfangwei.humpback.domain.page.model.block.BlockType

abstract class PageBlock (val id: String, var content: String, var type: BlockType) {}