package com.samsung.healthcare.account.domain

sealed class Role private constructor(val roleName: String) {
    companion object {
        const val ADMIN = "admin"
        const val SERVICE_ACCOUNT = "service-account"
        const val PROJECT_OWNER = "project-owner"
        const val HEAD_RESEARCHER = "head-researcher"
        const val RESEARCHER = "researcher"
    }

    object Admin : Role(ADMIN)

    // NOTE how to handle access control for service.
    object ServiceAccount : Role(SERVICE_ACCOUNT)

    sealed class ProjectRole private constructor(val projectId: String, val projectRoleName: String) :
        Role("$projectId$SEPARATOR$projectRoleName") {

        companion object {
            const val SEPARATOR = ':'
        }

        init {
            require(projectId.isNotBlank())
        }

        fun canAccessProject(pid: String): Boolean = projectId == pid

        class ProjectOwner(projectId: String) : ProjectRole(projectId, PROJECT_OWNER)

        class HeadResearcher(projectId: String) : ProjectRole(projectId, HEAD_RESEARCHER)

        class Researcher(projectId: String) : ProjectRole(projectId, RESEARCHER)

        class CustomRole(projectId: String, projectRoleName: String) : ProjectRole(projectId, projectRoleName) {
            init {
                require(projectRoleName.isNotBlank())
            }
        }
    }
}
