package com.samsung.healthcare.platform.adapter.persistence.converter.mapper

import com.samsung.healthcare.platform.adapter.persistence.entity.project.task.ItemEntity
import com.samsung.healthcare.platform.domain.project.task.Item
import com.samsung.healthcare.platform.domain.project.task.RevisionId
import com.samsung.healthcare.platform.enums.ItemType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class ItemMapperTest {
    @Test
    @Tag("positive")
    fun `should convert domain to entity`() {
        val item = Item(
            1,
            RevisionId.from(1),
            "2b3b286c-4000-454c-bd8e-875b123aa73c",
            "mapItem",
            mapOf("test" to "contents"),
            ItemType.QUESTION,
            0
        )

        val itemEntity = ItemMapper.INSTANCE.toEntity(item)

        assertAll(
            "Item mapping to entity",
            { assertEquals(item.id, itemEntity.id) },
            { assertEquals(item.revisionId.value, itemEntity.revisionId) },
            { assertEquals(item.taskId, itemEntity.taskId) },
            { assertEquals(item.name, itemEntity.name) },
            { assertEquals(item.contents, itemEntity.contents) },
            { assertEquals(item.type.name, itemEntity.type) },
            { assertEquals(item.sequence, itemEntity.sequence) }
        )
    }

    @Test
    @Tag("positive")
    fun `should convert entity to domain`() {
        val itemEntity = ItemEntity(
            1,
            1,
            "2b3b286c-4000-454c-bd8e-875b123aa73c",
            "mapItem",
            mapOf("test" to "contents"),
            "QUESTION",
            0
        )

        val item = ItemMapper.INSTANCE.toDomain(itemEntity)

        assertAll(
            "Item mapping to entity",
            { assertEquals(itemEntity.id, item.id) },
            { assertEquals(itemEntity.revisionId, item.revisionId.value) },
            { assertEquals(itemEntity.taskId, item.taskId) },
            { assertEquals(itemEntity.name, item.name) },
            { assertEquals(itemEntity.contents, item.contents) },
            { assertEquals(itemEntity.type, item.type.name) },
            { assertEquals(itemEntity.sequence, item.sequence) }
        )
    }
}
