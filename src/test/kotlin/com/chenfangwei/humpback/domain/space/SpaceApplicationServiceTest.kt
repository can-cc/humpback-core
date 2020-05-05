package com.chenfangwei.humpback.domain.space

import com.chenfangwei.humpback.domain.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.domain.space.model.Space
import com.chenfangwei.humpback.domain.space.repository.SpaceRepository
import com.chenfangwei.humpback.share.exception.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
internal class SpaceApplicationServiceTest(@Autowired spaceRepository: SpaceRepository) {
    private var spaceApplicationService: SpaceApplicationService

    init {
        this.spaceApplicationService = SpaceApplicationService(spaceRepository)
    }

    @Test
    fun createSpace(@Autowired spaceRepository: SpaceRepository) {
        spaceApplicationService.createSpace(CreateSpaceCommand("name_apple", "user_id_1113"))
        val spaces = spaceRepository.findByCreatorId("user_id_1113")
        assertThat(spaces.size).isEqualTo(1)
        assertThat(spaces[0].name).isEqualTo("name_apple")
        assertThat(spaces[0].creatorId).isEqualTo("user_id_1113")
    }

    @Test
    fun getSpaceDetail(@Autowired spaceRepository: SpaceRepository) {
        val space = spaceRepository.save(Space("name_ganze", "user_id_ganze_123"))
        val receivedSpace = spaceApplicationService.getSpaceDetail(space.id!!).get()
        assertThat(receivedSpace.name).isEqualTo("name_ganze")
        assertThat(receivedSpace.creatorId).isEqualTo("user_id_ganze_123")
    }

    @Test
    fun getSpaceList(@Autowired spaceRepository: SpaceRepository) {
        val space1 = Space("name_banana", "creator_id_123")
        val space2 = Space("name_orange", "creator_id_124")
        spaceRepository.save(space1)
        spaceRepository.save(space2)
        val spaces = spaceApplicationService.getSpaceList("creator_id_123")
        assertThat(spaces.size).isEqualTo(1)
        assertThat(spaces[0].name).isEqualTo("name_banana")
    }

    @Test
    fun checkUserCanReadSpace(@Autowired spaceRepository: SpaceRepository) {
        val space = Space("name_banana", "creator_id_123")
        spaceRepository.save(space)
        assertThat(spaceApplicationService.checkUserCanReadSpace("creator_id_123", space.id!!)).isTrue()
        assertThat(spaceApplicationService.checkUserCanReadSpace("creator_id_124", space.id!!)).isFalse()

        assertThrows<EntityNotFoundException> { spaceApplicationService.checkUserCanReadSpace("creator_id_123", "fake_space_id") }
    }

    @Test
    fun checkUserCanWriteSpace(@Autowired spaceRepository: SpaceRepository) {
        val space = Space("name_banana", "creator_id_123")
        spaceRepository.save(space)
        assertThat(spaceApplicationService.checkUserCanWriteSpace("creator_id_123", space.id!!)).isTrue()
        assertThat(spaceApplicationService.checkUserCanWriteSpace("creator_id_124", space.id!!)).isFalse()
    }
}