package com.chenfangwei.humpback.space.repository

import com.chenfangwei.humpback.space.model.Space
import org.springframework.data.mongodb.repository.MongoRepository

interface SpaceRepository : MongoRepository<Space, String> {
    fun findByCreatorId(id: String): List<Space>
}