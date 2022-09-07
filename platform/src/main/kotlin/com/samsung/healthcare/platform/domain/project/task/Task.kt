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

    fun unrollTask(): MutableMap<String, Any?> {
        val ret = mutableMapOf<String, Any?>()
        ret["revisionId"] = this.revisionId?.value
        ret["id"] = this.id
        ret["status"] = this.status
        ret["createdAt"] = this.createdAt
        ret["outdatedAt"] = this.outdatedAt
        ret["deletedAt"] = this.deletedAt
        this.properties.forEach {
            ret[it.key] = it.value
        }
        return ret
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
