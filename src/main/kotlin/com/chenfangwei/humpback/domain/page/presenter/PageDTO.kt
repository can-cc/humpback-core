package com.chenfangwei.humpback.domain.page.presenter;

import com.chenfangwei.humpback.domain.page.model.Page

data class PageDTO(private val page: Page) {
    val id: String = page.id!!
    val title: String = page.title!!
    val creatorId: String = page.creatorId
}
