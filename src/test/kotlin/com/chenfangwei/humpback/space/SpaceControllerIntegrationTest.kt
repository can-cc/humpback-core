package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.HumpbackApplication
import com.chenfangwei.humpback.space.command.CreateSpaceBody
import com.chenfangwei.humpback.space.model.Space
import com.chenfangwei.humpback.space.repository.SpaceRepository
import javafx.application.Application
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
internal class SpaceControllerIntegrationTest {

    @Test
    fun createSpace(@Autowired spaceRepository: SpaceRepository, @Autowired spaceController: SpaceController) {
        var spaceId = spaceController.createSpace(CreateSpaceBody("space_name_apple"), "user_id_123")
        val space = spaceRepository.findById(spaceId).get()
        assertThat(space.id!!).isEqualTo(spaceId)
        assertThat(space.name).isEqualTo("space_name_apple")
        assertThat(space.creatorId).isEqualTo("user_id_123")
    }
}