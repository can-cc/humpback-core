package com.chenfangwei.humpback.domain.page.command


data class UpdatePageCommand(val pageId: String, val spaceId: String, val title: String?) {
    lateinit var userId: String
}