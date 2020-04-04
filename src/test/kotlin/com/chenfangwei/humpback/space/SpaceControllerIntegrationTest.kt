package com.chenfangwei.humpback.space

import com.chenfangwei.humpback.space.model.Space
import com.chenfangwei.humpback.space.repository.SpaceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
internal class SpaceControllerIntegrationTest {

    @Test
    fun createSpace(@Autowired spaceRepository: SpaceRepository) {
        val savedSpace = spaceRepository.save(Space("space1", "apple_tom"))
        val space = spaceRepository.findById(savedSpace.id!!).get()
        assertThat(space.id!!).isEqualTo(savedSpace.id)
        assertThat(space.name).isEqualTo("space1")
        assertThat(space.creatorId).isEqualTo("apple_tom")
    }

}