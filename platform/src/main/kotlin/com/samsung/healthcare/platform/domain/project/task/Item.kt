package com.samsung.healthcare.platform.domain.project.task

import com.samsung.healthcare.platform.enums.ItemType

data class Item(
    val id: Int?,
    val revisionId: RevisionId,
    val taskId: String,
    val name: String,
    val contents: Map<String, Any>,
    val type: ItemType,
    val sequence: Int
) {
    companion object {
        private const val QUESTION_PREFIX = "Question"
        fun newItem(
            task: Task,
            contents: Map<String, Any>,
            type: ItemType,
            sequence: Int
        ): Item {
            requireNotNull(task.revisionId)
            return Item(null, task.revisionId, task.id, "$QUESTION_PREFIX$sequence", contents, type, sequence)
        }
    }

    init {
        // TODO: validate contents field by type
    }
}
