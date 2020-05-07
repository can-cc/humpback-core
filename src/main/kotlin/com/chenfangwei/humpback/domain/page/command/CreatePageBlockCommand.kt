package com.chenfangwei.humpback.domain.page.command

data class CreatePageBlockCommand(val spaceId: String, val pageId: String, var content: String) {
    lateinit var userId: String;
}