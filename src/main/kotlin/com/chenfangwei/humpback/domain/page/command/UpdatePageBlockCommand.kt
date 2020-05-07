package com.chenfangwei.humpback.domain.page.command

data class UpdatePageBlockCommand(val spaceId: String, val pageId: String, var blockId: String, var content: String) {
    lateinit var userId: String;
}