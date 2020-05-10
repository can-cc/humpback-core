package com.chenfangwei.humpback.domain.page.command

data class ResortPageBlockCommand(val spaceId: String, val pageId: String, val blockIds: ArrayList<String>) {
    lateinit var userId: String
}