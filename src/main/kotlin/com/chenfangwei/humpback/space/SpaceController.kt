package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.command.CreateSpaceBody
import com.chenfangwei.humpback.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.space.presenter.SpaceDTO
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
    fun createSpace(@RequestBody body: @Valid CreateSpaceBody, @RequestHeader("X-App-User-ID") userID: String): String {
        val command = CreateSpaceCommand(body.name, userID)
        return spaceApplicationService.createSpace(command)
    }

    @RequestMapping(value = ["/space/{spaceId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun querySpaceDetail(@PathVariable("spaceId") @Valid spaceId: String, @RequestHeader("X-App-User-ID") @Valid userID: String): SpaceDTO {
        // TODO: check space belong user
        val spaceOption = spaceApplicationService.getSpaceDetail(spaceId)
        if (spaceOption.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        val space = spaceOption.get()
        return SpaceDTO(space)
    }

    @RequestMapping(value = ["/spaces"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun querySpaces(@AuthenticationPrincipal() principal: Jwt): List<SpaceDTO> {
        return spaceApplicationService.getSpaceList(principal.id).map { SpaceDTO(it) }
    }
}