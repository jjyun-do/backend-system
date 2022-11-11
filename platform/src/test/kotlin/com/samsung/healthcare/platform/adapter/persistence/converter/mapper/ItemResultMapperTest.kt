package com.samsung.healthcare.platform.adapter.persistence.converter.mapper

import com.samsung.healthcare.platform.domain.project.task.ItemResult
import com.samsung.healthcare.platform.domain.project.task.RevisionId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class ItemResultMapperTest {
    @Test
    fun `should convert domain to entity`() {
        val itemResult = ItemResult(
            1,
            RevisionId.from(1),
            "2b3b286c-4000-454c-bd8e-875b123aa73c",
            "jjyun.do",
            "mapItem",
            "result"
        )

        val itemResultEntity = ItemResultMapper.INSTANCE.toEntity(itemResult)

        assertAll(
            "Item result mapping to entity",
            { assertEquals(itemResult.id, itemResultEntity.id) },
            { assertEquals(itemResult.revisionId.value, itemResultEntity.revisionId) },
            { assertEquals(itemResult.taskId, itemResultEntity.taskId) },
            { assertEquals(itemResult.userId, itemResultEntity.userId) },
            { assertEquals(itemResult.itemName, itemResultEntity.itemName) },
            { assertEquals(itemResult.result, itemResultEntity.result) }
        )
    }
}
