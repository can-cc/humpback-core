package com.chenfangwei.humpback.domain.page.command

data class CreatePageCommand(val title: String, val spaceId: String) {
    lateinit var creatorId: String
}