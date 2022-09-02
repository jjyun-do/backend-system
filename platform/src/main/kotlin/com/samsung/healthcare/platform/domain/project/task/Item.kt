package com.samsung.healthcare.platform.domain.project.task

import com.samsung.healthcare.platform.enums.ItemType

data class Item(
    val revisionId: RevisionId?,
    val taskId: String,
    val id: String,
    val contents: Map<String, Any>,
    val type: ItemType,
    val order: Int
) {
    companion object {
        private const val QUESTION_PREFIX = "Question"
        fun newItem(
            task: Task,
            contents: Map<String, Any>,
            type: ItemType,
            order: Int
        ): Item =
            Item(task.revisionId, task.id, "$QUESTION_PREFIX$order", contents, type, order)
    }

    init {
        // TODO: validate contents field by type
        requireNotNull(revisionId)
    }
}
