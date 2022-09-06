package com.samsung.healthcare.platform.adapter.persistence.repository.project.task

import com.samsung.healthcare.platform.adapter.persistence.entity.project.task.ItemEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ItemRepository : CoroutineCrudRepository<ItemEntity, Int> {
    suspend fun deleteAllByRevisionId(revisionId: Int)
}
