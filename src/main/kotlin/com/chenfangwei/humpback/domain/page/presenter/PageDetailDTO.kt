package com.chenfangwei.humpback.domain.page.presenter

import com.chenfangwei.humpback.domain.page.model.Page
import com.chenfangwei.humpback.domain.page.model.PageBlock

data class PageDetailDTO(private val page: Page) {
    val id: String = page.id!!
    val title: String = page.title!!
    val blocks: List<PageBlock>? = page.blocks
    val creatorId: String = page.creatorId
}