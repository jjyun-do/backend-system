package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.output.LoadProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GetProjectServiceTest {
    private val loadProjectPort = mockk<LoadProjectPort>()
    private val getProjectService = GetProjectService(loadProjectPort)

    @Test
    fun `should return project if project id is existed`() = runBlocking {
        val projectId = ProjectId.from(1)
        coEvery { loadProjectPort.loadProject(projectId) } returns Project(
            projectId,
            "project",
            emptyMap(),
            LocalDateTime.now()
        )
        val project = getProjectService.findProject(projectId)
        assertNotNull(project)
        assertEquals(projectId, project?.id)
    }

    @Test
    fun `should return null if id is not existed project`() = runBlocking {
        val projectId = ProjectId.from(1)
        coEvery { loadProjectPort.loadProject(projectId) } returns null
        assertNull(getProjectService.findProject(projectId))
    }
}
