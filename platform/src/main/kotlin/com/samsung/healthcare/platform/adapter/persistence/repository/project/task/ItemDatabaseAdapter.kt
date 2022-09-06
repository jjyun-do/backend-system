package com.samsung.healthcare.platform.adapter.persistence.repository.project.task

import com.samsung.healthcare.platform.adapter.persistence.entity.project.task.toEntity
import com.samsung.healthcare.platform.application.port.output.ItemOutputPort
import com.samsung.healthcare.platform.domain.project.task.Item
import org.springframework.stereotype.Component

@Component
class ItemDatabaseAdapter(
    val itemRepository: ItemRepository
) : ItemOutputPort {
    override suspend fun update(revisionId: Int, items: List<Item>) {
        itemRepository.deleteAllByRevisionId(revisionId)

        items.forEach {
            itemRepository.save(it.toEntity())
        }
    }
}
