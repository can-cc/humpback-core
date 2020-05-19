package com.chenfangwei.humpback.domain.space

import com.chenfangwei.humpback.domain.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.domain.space.command.UpdateSpaceCommand
import com.chenfangwei.humpback.domain.space.model.Space
import com.chenfangwei.humpback.domain.space.repository.SpaceRepository
import com.chenfangwei.humpback.share.exception.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpaceApplicationService(private val spaceRepository: SpaceRepository) {

    fun createSpace(command: CreateSpaceCommand): String {
        val space = Space(command.name, command.userID)
        spaceRepository.save(space)
        return space.id!!
    }

    fun updateSpace(command: UpdateSpaceCommand) {
        val space = getSpaceDetail(command.spaceId)
    }

    fun getSpaceDetail(spaceId: String): Space {
        val spaceOption = spaceRepository.findById(spaceId)
        if (spaceOption.isEmpty) {
            throw EntityNotFoundException("space not found")
        }
        return spaceOption.get()
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