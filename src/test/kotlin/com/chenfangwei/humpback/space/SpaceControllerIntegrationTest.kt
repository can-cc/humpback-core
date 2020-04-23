package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.command.CreateSpaceBody
import com.chenfangwei.humpback.space.repository.SpaceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.Jwt
import java.security.Principal

@SpringBootTest
internal class SpaceControllerIntegrationTest {

    @Test
    fun createSpace(@Autowired spaceRepository: SpaceRepository, @Autowired spaceController: SpaceController) {
        val spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), "user_id_123")
        val space = spaceRepository.findById(spaceId).get()
        assertThat(space.id!!).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
        assertThat(space.creatorId).isEqualTo("user_id_123")
    }

    @Test
    fun querySpaceDetail(@Autowired spaceController: SpaceController) {
        val spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), "user_id_123")
        val space = spaceController.querySpaceDetail(spaceId, "user_id_123")
        assertThat(space.id).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
    }
//
//    @Test
//    fun querySpaces(@Autowired spaceController: SpaceController) {
//        spaceController.createSpace(CreateSpaceBody("space_name_banana1"), "user_id_2")
//        spaceController.createSpace(CreateSpaceBody("space_name_banana2"), "user_id_2")
//        var principal = Jwt()
//        principal.id = "user_i1_2";
//        val spaces = spaceController.querySpaces()
//        assertThat(spaces.size).isEqualTo(2)
//    }
}