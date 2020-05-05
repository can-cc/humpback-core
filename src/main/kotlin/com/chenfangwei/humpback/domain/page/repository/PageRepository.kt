package com.chenfangwei.humpback.domain.page.repository

import com.chenfangwei.humpback.domain.page.model.Page
import org.springframework.data.mongodb.repository.MongoRepository

interface PageRepository : MongoRepository<Page, String> {
    fun findBySpaceId(spaceId: String): List<Page>
}