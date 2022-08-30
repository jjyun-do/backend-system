package com.samsung.healthcare.account.domain

import com.samsung.healthcare.account.domain.Role.Admin
import com.samsung.healthcare.account.domain.Role.ProjectRole.HeadResearcher
import com.samsung.healthcare.account.domain.Role.ProjectRole.ProjectOwner
import com.samsung.healthcare.account.domain.Role.ServiceAccount
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

internal class AccountTest {
    @Test
    fun `Account with service account role is able to create role`() {
        assertTrue(
            accountWith(ServiceAccount).canCreateRole()
        )
    }

    @Test
    fun `Account without service account role is not able to create role`() {
        assertFalse(
            accountWith(Admin).canCreateRole()
        )
    }

    @Test
    fun `Account with project owner on project-id is able to assign role for that project`() {
        val projectId = "project-x"
        assertTrue(
            accountWith(ProjectOwner(projectId))
                .canAssignProjectRole(projectId)
        )
    }

    @Test
    fun `Account with project owner on project-id is not able to assign role for other project`() {
        val projectId = "project-x"
        assertFalse(
            accountWith(ProjectOwner(projectId))
                .canAssignProjectRole("project-y")
        )
    }

    @Test
    fun `Account with head-research on project-id is not able to assign role for that project`() {
        val projectId = "project-x"
        assertFalse(
            accountWith(HeadResearcher(projectId))
                .canAssignProjectRole(projectId)
        )
    }

    private fun accountWith(vararg roles: Role): Account =
        Account(
            UUID.randomUUID().toString(),
            Email("someone@gmail.com"),
            roles.asList()
        )
}
