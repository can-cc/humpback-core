package com.chenfangwei.humpback.domain.space

import com.chenfangwei.humpback.domain.space.command.CreateSpaceBody
import com.chenfangwei.humpback.domain.space.repository.SpaceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.security.oauth2.jwt.Jwt

@DataMongoTest
internal class SpaceControllerTest(@Autowired spaceRepository: SpaceRepository) {
    private var spaceController: SpaceController

    init {
        val spaceApplicationService = SpaceApplicationService(spaceRepository)
        this.spaceController = SpaceController(spaceApplicationService)
    }

    @Test
    fun createSpace(@Autowired spaceRepository: SpaceRepository) {
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        val spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), principal)
        val space = spaceRepository.findById(spaceId).get()
        assertThat(space.id!!).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
        assertThat(space.creatorId).isEqualTo("user_id_123")
    }

    @Test
    fun querySpaceDetail() {
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        val spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), principal)
        val space = spaceController.querySpaceDetail(spaceId, principal)
        assertThat(space.id).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
    }

    @Test
    fun querySpaces() {
        val principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        spaceController.createSpace(CreateSpaceBody("space_name_banana1"), principal)
        spaceController.createSpace(CreateSpaceBody("space_name_banana2"), principal)
        val spaces = spaceController.querySpaces(principal)
        assertThat(spaces.size).isEqualTo(2)
    }
}