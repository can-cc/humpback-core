package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.command.CreateSpaceBody
import com.chenfangwei.humpback.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.space.presenter.SpaceDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class SpaceController(val spaceApplicationService: SpaceApplicationService) {

    @RequestMapping(value = ["/space"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    fun createSpace(@RequestBody body: @Valid CreateSpaceBody, @RequestHeader("X-App-User-ID") userID: String) {
        val command = CreateSpaceCommand(body.name, userID)
        spaceApplicationService.createSpace(command)
    }


    @RequestMapping(value = ["/space/{sid}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun querySpaceDetail(@PathVariable("sid") @Valid sid: String, @RequestHeader("X-App-User-ID") @Valid userID: String): SpaceDTO {
        val spaceOption = spaceApplicationService.getSpaceDetail(sid)
        val space = spaceOption.get()
        // TODO 404
        return SpaceDTO(space.id!!, space.name)
    }
}