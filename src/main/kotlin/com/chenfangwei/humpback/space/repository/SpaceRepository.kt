package com.chenfangwei.humpback.space.repository

import com.chenfangwei.humpback.space.Space
import org.springframework.data.mongodb.repository.MongoRepository

interface SpaceRepository : MongoRepository<Space, String> {
    fun findByID(slug: String): Space?
}