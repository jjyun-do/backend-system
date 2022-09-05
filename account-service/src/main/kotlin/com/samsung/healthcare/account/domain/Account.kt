package com.samsung.healthcare.account.domain

import com.samsung.healthcare.account.domain.Role.ProjectRole.ProjectOwner
import com.samsung.healthcare.account.domain.Role.ServiceAccount

data class Account(
    val id: String,
    val email: Email,
    val roles: Collection<Role>
) {
    fun canAssignProjectRole(projectId: String): Boolean =
        roles.filterIsInstance<ProjectOwner>()
            .any { it.canAccessProject(projectId) }

    fun canCreateRole(): Boolean =
        roles.contains(ServiceAccount)
}
