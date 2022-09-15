package com.samsung.healthcare.platform.domain.project.task

data class ItemResult(
    val id: Int?,
    val revisionId: RevisionId,
    val taskId: String,
    val userId: String,
    val itemName: String,
    val result: String,
) {
    companion object {
        fun newItemResult(
            taskResult: TaskResult,
            itemName: String,
            result: String,
        ): ItemResult {
            return ItemResult(null, taskResult.revisionId, taskResult.taskId, taskResult.userId, itemName, result)
        }
    }
}
