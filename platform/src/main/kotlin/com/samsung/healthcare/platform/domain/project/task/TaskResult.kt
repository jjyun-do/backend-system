package com.samsung.healthcare.platform.domain.project.task

import java.time.LocalDateTime

data class TaskResult(
    val id: Int?,
    val revisionId: RevisionId,
    val taskId: String,
    val userId: String,
    val startedAt: LocalDateTime,
    val submittedAt: LocalDateTime,
) {
    companion object {
        fun newTaskResult(
            revisionId: RevisionId,
            taskId: String,
            userId: String,
            startedAt: LocalDateTime,
            submittedAt: LocalDateTime,
        ): TaskResult =
            TaskResult(null, revisionId, taskId, userId, startedAt, submittedAt)
    }
}
