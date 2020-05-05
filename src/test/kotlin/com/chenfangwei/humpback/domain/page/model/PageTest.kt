package com.chenfangwei.humpback.domain.page.model

import com.chenfangwei.humpback.domain.page.exception.InvalidPageOperationException
import org.junit.jupiter.api.Test

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows


internal class PageTest {

    @Test
    fun addBlockContent() {
        val page = Page("creator_id_123", "page_id_123")
        page.addBlockContent("hi")
        assertThat(page.blocks!!.size).isEqualTo(1)
        assertThat(page.blocks!![0].content).isEqualTo("hi")
        assertThat(page.blocks!![0].id).isNotEmpty()

        page.addBlockContent("second")
        assertThat(page.blocks!!.size).isEqualTo(2)
        assertThat(page.blocks!![1].content).isEqualTo("second")

        page.addBlockContent("end", page.blocks!![0].id)
        assertThat(page.blocks!!.size).isEqualTo(3)
        assertThat(page.blocks!![1].content).isEqualTo("end")

        assertThrows<InvalidPageOperationException> { -> page.addBlockContent("exception", "invalid id")}
    }
}