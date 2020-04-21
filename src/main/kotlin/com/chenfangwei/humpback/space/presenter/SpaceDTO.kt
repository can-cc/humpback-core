package com.chenfangwei.humpback.space.presenter

import com.chenfangwei.humpback.space.model.Space

data class SpaceDTO(private val space: Space) {
    var id: String = space.id!!
    var name: String = space.name
}