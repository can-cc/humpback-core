package com.chenfangwei.humpback.domain.page

import com.chenfangwei.humpback.domain.page.command.*
import com.chenfangwei.humpback.domain.page.presenter.PageDTO
import com.chenfangwei.humpback.domain.page.presenter.PageDetailDTO
import com.chenfangwei.humpback.share.exception.BadRequestException
import com.chenfangwei.humpback.storage.StorageService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.ByteArrayInputStream
import javax.validation.Valid


@RestController
class PageController(private val pageApplicationService: PageApplicationService, private val storageService: StorageService) {

    @RequestMapping(value = ["/page"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPage(@RequestBody command: @Valid CreatePageCommand, @AuthenticationPrincipal principal: Jwt): String {
        val userId = principal.getClaimAsString("sub")
        command.creatorId = userId
        return pageApplicationService.createPage(command)
    }

    @RequestMapping(value = ["/space/{spaceId}/pages"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun queryPages(@PathVariable("spaceId") @Valid spaceId: String, @AuthenticationPrincipal() principal: Jwt): List<PageDTO> {
        val userId = principal.getClaimAsString("sub")
        return pageApplicationService.pageList(spaceId, userId).map { PageDTO(it) }
    }


    @RequestMapping(value = ["/space/{spaceId}/page/{pageId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun queryPageDetail(@PathVariable("spaceId") @Valid spaceId: String, @PathVariable("pageId") @Valid pageId: String, @AuthenticationPrincipal() principal: Jwt): PageDetailDTO {
        val userId = principal.getClaimAsString("sub")
        return PageDetailDTO(pageApplicationService.pageDetail(spaceId, pageId, userId))
    }

    @RequestMapping(value = ["/page/{pageId}"], method = [RequestMethod.PATCH], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePage(@PathVariable("pageId") @Valid pageId: String, @RequestBody command: @Valid UpdatePageCommand, @AuthenticationPrincipal() principal: Jwt) {
        val userId = principal.getClaimAsString("sub")
        command.userId = userId
        pageApplicationService.updatePage(command)
    }

    @RequestMapping(value = ["/page/{pageId}/block"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPageBlock(@PathVariable("pageId") @Valid pageId: String, @RequestBody command: @Valid CreatePageBlockCommand, @AuthenticationPrincipal principal: Jwt): String {
        val userId = principal.getClaimAsString("sub")
        command.userId = userId
        return pageApplicationService.createPageBlock(command)
    }

    @RequestMapping(value = ["/page/{pageId}/block/{blockId}"], method = [RequestMethod.PUT], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePageBlock(@RequestBody command: @Valid UpdatePageBlockCommand, @AuthenticationPrincipal() principal: Jwt) {
        val userId = principal.getClaimAsString("sub")
        command.userId = userId
        pageApplicationService.updatePageBlock(command)
    }

    @RequestMapping(value = ["/page/{pageId}/blocks/resort"], method = [RequestMethod.POST])
    fun resortPageBlock(@RequestBody command: @Valid ResortPageBlockCommand, @AuthenticationPrincipal() principal: Jwt, @PathVariable pageId: String) {
        val userId = principal.getClaimAsString("sub")
        command.userId = userId
        pageApplicationService.resortPageBlock(command)
    }

    @RequestMapping(value = ["/page/{pageId}/block/image"], method = [RequestMethod.POST])
    fun Upload(request: MultipartHttpServletRequest, @PathVariable pageId: String): String {
        val multipartFile = request.getFile("data")
        if (multipartFile != null) {
            return storageService.saveObject(ByteArrayInputStream(multipartFile.bytes), multipartFile.contentType)
        } else {
            throw BadRequestException()
        }
    }
}