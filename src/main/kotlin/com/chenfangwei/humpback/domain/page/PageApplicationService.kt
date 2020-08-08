package com.chenfangwei.humpback.domain.page

import com.chenfangwei.humpback.domain.page.command.*
import com.chenfangwei.humpback.domain.page.model.Page
import com.chenfangwei.humpback.domain.page.model.block.BlockFactory
import com.chenfangwei.humpback.domain.page.repository.PageRepository
import com.chenfangwei.humpback.domain.space.SpaceApplicationService
import com.chenfangwei.humpback.share.exception.EntityNotFoundException
import com.chenfangwei.humpback.share.exception.ForbiddenException
import org.springframework.stereotype.Service

@Service
class PageApplicationService(private val pageRepository: PageRepository,
                             private val spaceApplicationService: SpaceApplicationService,
                             private val blockFactory: BlockFactory) {

    fun createPage(command: CreatePageCommand): String {
        val page = Page(command.creatorId, command.spaceId)
        page.title = command.title
        pageRepository.save(page)
        return page.id!!
    }

    fun pageList(spaceId: String, userId: String): List<Page> {
        if (!spaceApplicationService.checkUserCanReadSpace(userId, spaceId)) {
            throw ForbiddenException("forbidden access space pages")
        }
        return pageRepository.findBySpaceId(spaceId)
    }

    fun pageDetail(spaceId: String, pageId: String, userId: String): Page {
        return findPageAndCheckPermission(userId, pageId)
    }

    fun updatePage(command: UpdatePageCommand) {
        val page = findPageAndCheckPermission(command.userId, command.pageId)
        if (command.title != null) {
            page.title = command.title
        }
        pageRepository.save(page)
    }

    fun createPageBlock(command: CreatePageBlockCommand): String {
        val page = findPageAndCheckPermission(command.userId,  command.pageId)
        val block = blockFactory.createBlock(command.blockType, command.content)
        page.addBlock(block, command.previousBlockId)
        pageRepository.save(page)
        return block.id
    }

    fun updatePageBlock(command: UpdatePageBlockCommand) {
        val page = findPageAndCheckPermission(command.userId,  command.pageId)
        page.updateBlock(command.blockId, command.content)
        pageRepository.save(page)
    }


    fun resortPageBlock(command: ResortPageBlockCommand) {
        val page = findPageAndCheckPermission(command.userId, command.pageId)
        page.resortBlocks(command.blockIds)
        pageRepository.save(page)
    }

    fun deletePageBlock(pageId: String, blockId: String, userId: String) {
        val page = findPageAndCheckPermission(userId, pageId);
        page.deleteBlock(blockId)
    }

    private fun findPageAndCheckPermission(userId: String, pageId: String): Page {
        val page = pageRepository.findById(pageId).orElseThrow { -> EntityNotFoundException("page not found") }
        if (!spaceApplicationService.checkUserCanWriteSpace(userId, page.spaceId)) {
            throw ForbiddenException("forbidden access space pages")
        }
        return page
    }
}