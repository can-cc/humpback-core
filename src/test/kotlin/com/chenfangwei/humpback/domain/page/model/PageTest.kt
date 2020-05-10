package com.chenfangwei.humpback.domain.page.model

import com.chenfangwei.humpback.domain.page.exception.InvalidPageOperationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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

    @Test
    fun updateBlock() {
        val page = Page("creator_id_123_update_block", "page_id_123")
        page.addBlockContent("hi")
        page.updateBlock(page.blocks!![0].id, "hello")
        assertThat(page.blocks!![0].content).isEqualTo("hello")

        assertThrows<InvalidPageOperationException> { -> page.updateBlock("not_exist_id", "hello") }
    }

    @Test
    fun resortBlocks() {
        val page = Page("creator_id_123_update_block", "page_id_123")
        page.blocks = arrayListOf(PageBlock("1", ""), PageBlock("2", ""), PageBlock("3", ""))
        page.resortBlocks(arrayListOf("2", "3", "1"))
        assertThat(page.blocks!!.map { b-> b.id}.joinToString("")).isEqualTo("231")
    }
}