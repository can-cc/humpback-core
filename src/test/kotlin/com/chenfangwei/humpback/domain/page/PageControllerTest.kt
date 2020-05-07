package com.chenfangwei.humpback.domain.page

import com.chenfangwei.humpback.domain.page.command.CreatePageBlockCommand
import com.chenfangwei.humpback.domain.page.command.CreatePageCommand
import com.chenfangwei.humpback.domain.page.command.UpdatePageCommand
import com.chenfangwei.humpback.domain.page.model.Page
import com.chenfangwei.humpback.domain.page.repository.PageRepository
import com.chenfangwei.humpback.domain.space.SpaceApplicationService
import com.chenfangwei.humpback.domain.space.model.Space
import com.chenfangwei.humpback.domain.space.repository.SpaceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.security.oauth2.jwt.Jwt

@DataMongoTest
internal class PageControllerTest(@Autowired pageRepository: PageRepository, @Autowired spaceRepository: SpaceRepository) {
    private var pageController: PageController

    init {
        val spaceApplicationService = SpaceApplicationService(spaceRepository)
        val pageApplicationService = PageApplicationService(pageRepository, spaceApplicationService)
        this.pageController = PageController(pageApplicationService)
    }

    @Test
    fun createSpace(@Autowired pageRepository: PageRepository) {
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        val pageId = pageController.createPage(CreatePageCommand("", "space_id_1"), principal)
        val page = pageRepository.findById(pageId).get()
        assertThat(page.id).isEqualTo(pageId)
        assertThat(page.title).isEqualTo("")
    }

    @Test
    fun querySpaces(@Autowired pageRepository: PageRepository, @Autowired spaceRepository: SpaceRepository) {
        val space = Space("space1", "user_id_923")
        spaceRepository.save(space)
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_923")
        val page1 = Page("user_id_923", space.id!!)
        page1.title = "time"
        val page2 = Page("user_id_923", space.id!!)
        page2.title = "time2"
        pageRepository.save(page1)
        pageRepository.save(page2)
        val pageDtoList = pageController.queryPages(space.id!!, principal)
        assertThat(pageDtoList.map { p -> p.title }).isEqualTo(listOf("time", "time2"))
    }

    @Test
    fun updatePage(@Autowired pageRepository: PageRepository, @Autowired spaceRepository: SpaceRepository) {
        val space = Space("space1", "user_id_92_3")
        spaceRepository.save(space)
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_92_3")
        val page = Page("user_id_92_3", space.id!!)
        page.title = ""
        pageRepository.save(page)
        val command = UpdatePageCommand(page.id!!, space.id!!, "你好")
        command.userId = "user_id_92_3"
        pageController.updatePage(page.id!!, command, principal)
        val updatedPage = pageRepository.findById(page.id!!).get()
        assertThat(updatedPage.title).isEqualTo("你好")
    }

    @Test
    fun createPageBlock() {
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_92_03")
        val command = CreatePageBlockCommand("space_id_123","page_id_123", "你好")
        command.userId = "user_id_92_3"

        val pageApplicationService = Mockito.mock(PageApplicationService::class.java)
        val pageController = PageController(pageApplicationService)
        pageController.createPageBlock("page_id_123", command, principal)
        Mockito.verify(pageApplicationService).createPageBlock(command)
    }
}