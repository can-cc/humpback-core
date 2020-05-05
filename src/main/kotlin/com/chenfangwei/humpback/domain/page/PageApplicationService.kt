package com.chenfangwei.humpback.domain.page

import com.chenfangwei.humpback.domain.page.command.CreatePageCommand
import com.chenfangwei.humpback.domain.page.model.Page
import com.chenfangwei.humpback.domain.page.repository.PageRepository
import com.chenfangwei.humpback.domain.space.SpaceApplicationService
import com.chenfangwei.humpback.share.exception.ForbiddenException
import org.springframework.stereotype.Service

@Service
class PageApplicationService(private val pageRepository: PageRepository, private val spaceApplicationService: SpaceApplicationService) {

    fun createPage(createPageCommand: CreatePageCommand): String {
        val page = Page(createPageCommand.creatorId, createPageCommand.spaceId)
        page.title = createPageCommand.title
        pageRepository.save(page)
        return page.id!!
    }

    fun queryPageList(spaceId: String, userId: String): List<Page> {
        if (!spaceApplicationService.checkUserCanReadSpace(userId, spaceId)) {
            throw ForbiddenException("forbidden access space pages")
        }
        return pageRepository.findBySpaceId(spaceId)
    }
}