package com.chenfangwei.humpback.domain.space

import com.chenfangwei.humpback.domain.space.command.CreateSpaceBody
import com.chenfangwei.humpback.domain.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.domain.space.presenter.SpaceDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
class SpaceController(val spaceApplicationService: SpaceApplicationService) {

    @RequestMapping(value = ["/space"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    fun createSpace(@RequestBody body: @Valid CreateSpaceBody, @AuthenticationPrincipal() principal: Jwt): String {
        val userId = principal.getClaimAsString("sub")
        val command = CreateSpaceCommand(body.name, userId)
        return spaceApplicationService.createSpace(command)
    }

    @RequestMapping(value = ["/space/{spaceId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun querySpaceDetail(@PathVariable("spaceId") @Valid spaceId: String, @AuthenticationPrincipal() principal: Jwt): SpaceDTO {
        // TODO: check space belong user
        val userId = principal.getClaimAsString("sub")
        val space = spaceApplicationService.getSpaceDetail(spaceId)
        return SpaceDTO(space)
    }

    @RequestMapping(value = ["/spaces"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun querySpaces(@AuthenticationPrincipal() principal: Jwt): List<SpaceDTO> {
        val userId = principal.getClaimAsString("sub")
        return spaceApplicationService.getSpaceList(userId).map { SpaceDTO(it) }
    }
}