package com.chenfangwei.humpback.domain.space.repository

import com.chenfangwei.humpback.domain.space.model.Space
import org.springframework.data.mongodb.repository.MongoRepository

interface SpaceRepository : MongoRepository<Space, String> {
    fun findByCreatorId(id: String): List<Space>
}