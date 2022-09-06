package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.project.task.Item

interface ItemOutputPort {
    suspend fun update(revisionId: Int, items: List<Item>)
}
