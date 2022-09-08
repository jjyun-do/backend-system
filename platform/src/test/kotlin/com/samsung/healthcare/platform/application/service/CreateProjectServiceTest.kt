package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role.TeamAdmin
import com.samsung.healthcare.platform.application.exception.UnauthorizedException
import com.samsung.healthcare.platform.application.port.input.CreateProjectCommand
import com.samsung.healthcare.platform.application.port.output.CreateProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import io.mockk.MockKMatcherScope
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CreateProjectServiceTest {
    private val createProjectPort = mockk<CreateProjectPort>()
    private val createProjectService = CreateProjectService(createProjectPort)

    @Test
    fun `should return new project id`() = runBlocking {
        val projectId = ProjectId.from(3)
        val createProjectCommand = CreateProjectCommand("project", mapOf("key" to "value"))

        coEvery {
            createProjectPort.create(isMatchProjectNameAndInfo(createProjectCommand))
        } returns projectId

        val account = Account(
            "account-id",
            Email("cubist@test.com"),
            listOf(TeamAdmin)
        )

        val project =
            ContextHolder.setAccount(mono { createProjectService.registerProject(createProjectCommand) }, account)
                .awaitSingle()
        assertEquals(
            projectId,
            project
        )
    }

    @Test
    fun `should throw unauthrized when account do not have team admin`() = runBlocking {
        val projectId = ProjectId.from(3)
        val createProjectCommand = CreateProjectCommand("project", mapOf("key" to "value"))
        assertThrows<UnauthorizedException>("should throw an exception") {
            createProjectService.registerProject(createProjectCommand)
        }
    }

    private fun MockKMatcherScope.isMatchProjectNameAndInfo(createProjectCommand: CreateProjectCommand): Project =
        match { (_, name, info) ->
            name == createProjectCommand.name && info == createProjectCommand.info
        }
}
