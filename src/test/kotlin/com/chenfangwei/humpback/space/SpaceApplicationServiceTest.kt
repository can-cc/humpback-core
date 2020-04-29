package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.command.CreateSpaceCommand
import com.chenfangwei.humpback.space.model.Space
import com.chenfangwei.humpback.space.repository.SpaceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
internal class SpaceApplicationServiceTest {
    private var spaceApplicationService: SpaceApplicationService

    constructor(@Autowired spaceRepository: SpaceRepository) {
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
        var receivedSpace = spaceApplicationService.getSpaceDetail(space.id!!).get()
        assertThat(receivedSpace.name).isEqualTo("name_ganze")
        assertThat(receivedSpace.creatorId).isEqualTo("user_id_ganze_123")
    }

    @Test
    fun getSpaceList(@Autowired spaceRepository: SpaceRepository) {
        val space1 = Space("name_banana", "creator_id_123")
        val space2 = Space("name_orange", "creator_id_124")
        spaceRepository.save(space1)
        spaceRepository.save(space2)
        var spaces = spaceApplicationService.getSpaceList("creator_id_123")
        assertThat(spaces.size).isEqualTo(1)
        assertThat(spaces[0].name).isEqualTo("name_banana")
    }
}