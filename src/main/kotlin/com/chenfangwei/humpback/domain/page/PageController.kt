package com.chenfangwei.humpback.domain.page

import com.chenfangwei.humpback.domain.page.command.CreatePageCommand
import com.chenfangwei.humpback.domain.page.presenter.PageDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
class PageController(private val pageApplicationService: PageApplicationService) {

    @RequestMapping(value = ["/page"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    fun createSpace(@RequestBody command: @Valid CreatePageCommand, @AuthenticationPrincipal principal: Jwt): String {
        val userId = principal.getClaimAsString("sub")
        command.creatorId = userId
        return pageApplicationService.createPage(command)
    }

    @RequestMapping(value = ["/space/{spaceId}/pages"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun querySpaces(@PathVariable("spaceId") @Valid spaceId: String, @AuthenticationPrincipal() principal: Jwt): List<PageDTO> {
        val userId = principal.getClaimAsString("sub")
        return pageApplicationService.queryPageList(spaceId, userId).map { PageDTO(it) }
    }
}