package com.chenfangwei.humpback.domain.space.presenter

import com.chenfangwei.humpback.domain.space.model.Space

data class SpaceDTO(private val space: Space) {
    var id: String = space.id!!
    var name: String = space.name
}