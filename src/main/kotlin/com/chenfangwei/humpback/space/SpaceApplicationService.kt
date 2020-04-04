package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.space.model.Space
import com.chenfangwei.humpback.space.repository.SpaceRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpaceApplicationService(private val spaceRepository: SpaceRepository) {

    fun createSpace(createSpaceCommand: CreateSpaceCommand) {
        val space = Space(createSpaceCommand.name, createSpaceCommand.userID)
        spaceRepository.save(space)
    }

    fun getSpaceDetail(spaceId: String): Optional<Space> {
       return spaceRepository.findById(spaceId)
    }
}