package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.command.CreateSpaceBody
import com.chenfangwei.humpback.space.repository.SpaceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
internal class SpaceControllerIntegrationTest {
    private var spaceController: SpaceController

    constructor(@Autowired spaceRepository: SpaceRepository) {
        val spaceApplicationService = SpaceApplicationService(spaceRepository)
        this.spaceController = SpaceController(spaceApplicationService)
    }


    @Test
    fun createSpace(@Autowired spaceRepository: SpaceRepository) {
        var principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        val spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), principal)
        val space = spaceRepository.findById(spaceId).get()
        assertThat(space.id!!).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
        assertThat(space.creatorId).isEqualTo("user_id_123")
    }

    @Test
    fun querySpaceDetail() {
        var principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        val spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), principal)
        val space = spaceController.querySpaceDetail(spaceId, principal)
        assertThat(space.id).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
    }

    @Test
    fun querySpaces() {
        var principal = Mockito.mock(Jwt::class.java)
        Mockito.`when`(principal.getClaimAsString("sub")).thenReturn("user_id_123")
        spaceController.createSpace(CreateSpaceBody("space_name_banana1"), principal)
        spaceController.createSpace(CreateSpaceBody("space_name_banana2"), principal)
        val spaces = spaceController.querySpaces(principal)
        assertThat(spaces.size).isEqualTo(2)
    }
}