package com.samsung.healthcare.platform.domain.project.task

import com.samsung.healthcare.platform.enums.TaskStatus
import java.time.LocalDateTime
import java.util.UUID

data class Task(
    val revisionId: RevisionId?,
    val id: String,
    val properties: Map<String, Any>,
    val status: TaskStatus,
    val createdAt: LocalDateTime? = null,
    val outdatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun newTask(): Task =
            Task(
                null,
                UUID.randomUUID().toString(),
                emptyMap(),
                TaskStatus.DRAFT
            )
    }

    init {
        // TODO: validate properties field
    }
}

class RevisionId private constructor(val value: Int) {
    companion object {
        fun from(value: Int?): RevisionId {
            requireNotNull(value)
            require(0 < value)
            return RevisionId(value)
        }
    }
}
