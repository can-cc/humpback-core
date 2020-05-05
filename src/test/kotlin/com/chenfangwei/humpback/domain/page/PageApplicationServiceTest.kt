package com.chenfangwei.humpback.domain.page

import com.chenfangwei.humpback.domain.page.command.CreatePageCommand
import com.chenfangwei.humpback.domain.page.model.Page
import com.chenfangwei.humpback.domain.page.repository.PageRepository
import com.chenfangwei.humpback.domain.space.SpaceApplicationService
import com.chenfangwei.humpback.domain.space.model.Space
import com.chenfangwei.humpback.domain.space.repository.SpaceRepository
import com.chenfangwei.humpback.share.exception.ForbiddenException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
internal class PageApplicationServiceTest(@Autowired pageRepository: PageRepository, @Autowired spaceRepository: SpaceRepository) {
    private var pageApplicationService: PageApplicationService

    init {
        val spaceApplicationService = SpaceApplicationService(spaceRepository)
        this.pageApplicationService = PageApplicationService(pageRepository, spaceApplicationService)
    }

    @Test
    fun createPage(@Autowired pageRepository: PageRepository) {
        val createPageCommand = CreatePageCommand("", "space_id_123")
        createPageCommand.creatorId = "creator_123"
        val pageId = pageApplicationService.createPage(createPageCommand)
        val receivedPage = pageRepository.findById(pageId).get()
        assertThat(receivedPage.title).isEqualTo("")
        assertThat(receivedPage.spaceId).isEqualTo("space_id_123")
        assertThat(receivedPage.creatorId).isEqualTo("creator_123")
    }

    @Test
    fun queryPageList(@Autowired pageRepository: PageRepository, @Autowired spaceRepository: SpaceRepository) {
        val space = Space("space_banana", "user_111")
        spaceRepository.save(space)
        val page1 = Page("user_111", space.id!!)
        page1.title = "Page1"
        val page2 = Page("user_111", space.id!!)
        page2.title = "Page2"
        pageRepository.save(page1)
        pageRepository.save(page2)
        val pages = this.pageApplicationService.queryPageList(space.id!!, "user_111")
        assertThat(pages.map { p -> p.title }).isEqualTo(listOf("Page1", "Page2"))

        assertThrows<ForbiddenException> { -> this.pageApplicationService.queryPageList(space.id!!, "user_112") }
    }
}