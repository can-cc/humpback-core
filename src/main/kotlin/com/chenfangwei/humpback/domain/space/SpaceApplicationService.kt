package com.chenfangwei.humpback.domain.space

import com.chenfangwei.humpback.domain.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.domain.space.model.Space
import com.chenfangwei.humpback.domain.space.repository.SpaceRepository
import com.chenfangwei.humpback.share.exception.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpaceApplicationService(private val spaceRepository: SpaceRepository) {

    fun createSpace(createSpaceCommand: CreateSpaceCommand): String {
        val space = Space(createSpaceCommand.name, createSpaceCommand.userID)
        spaceRepository.save(space)
        return space.id!!
    }

    fun getSpaceDetail(spaceId: String): Optional<Space> {
       return spaceRepository.findById(spaceId)
    }

    fun getSpaceList(creatorId: String): List<Space> {
        return spaceRepository.findByCreatorId(creatorId)
    }

    fun checkUserCanReadSpace(userId: String, spaceId: String): Boolean {
        val spaceOption = spaceRepository.findById(spaceId)
        if (spaceOption.isEmpty) {
            throw EntityNotFoundException("space not found")
        }
        val space = spaceOption.get()
        if (space.creatorId == userId) {
            return true
        }
        return false
    }

    fun checkUserCanWriteSpace(userId: String, spaceId: String): Boolean {
        // TODO: change it feature
        return checkUserCanReadSpace(userId, spaceId)
    }
}