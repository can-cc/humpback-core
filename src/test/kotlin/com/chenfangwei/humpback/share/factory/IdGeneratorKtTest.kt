package com.chenfangwei.humpback.share.factory

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class IdGeneratorKtTest {

    @Test
    fun generateUUID_not_null() {
        val uuid = generateId()
        assertThat(uuid).isNotNull()
    }
}