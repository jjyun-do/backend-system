package com.samsung.healthcare.platform.domain

import java.time.LocalDateTime

data class Project(
    val id: ProjectId?,
    val name: String,
    val info: Map<String, Any>,
    val createdAt: LocalDateTime,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun newProject(name: String, info: Map<String, Any>): Project =
            Project(null, name, info, LocalDateTime.now())
    }

    data class ProjectId private constructor(val value: Int) {
        companion object {
            fun from(value: Int?): ProjectId {
                requireNotNull(value)
                require(0 < value)
                return ProjectId(value)
            }
        }

        override fun toString(): String =
            value.toString()
    }
}
