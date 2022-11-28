package com.samsung.healthcare.platform.application.service.project.task

import com.samsung.healthcare.platform.NEGATIVE_TEST
import com.samsung.healthcare.platform.POSITIVE_TEST
import com.samsung.healthcare.platform.application.port.output.project.task.TaskOutputPort
import com.samsung.healthcare.platform.domain.project.task.RevisionId
import com.samsung.healthcare.platform.domain.project.task.Task
import com.samsung.healthcare.platform.enums.TaskStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

internal class CreateTaskServiceTest {
    private val taskOutputPort = mockk<TaskOutputPort>()
    private val createTaskService = CreateTaskService(
        taskOutputPort
    )

    @Test
    @Tag(POSITIVE_TEST)
    fun `should match properties of generated task`() = runTest {
        val revisionId = RevisionId.from(1)
        val task = Task(
            revisionId,
            "task-id",
            emptyMap(),
            TaskStatus.DRAFT
        )
        coEvery {
            taskOutputPort.create(any())
        } returns task

        val response = createTaskService.createTask()

        assertAll(
            "TaskResponse properties",
            { assertEquals(revisionId.value, response.revisionId) },
            { assertEquals(task.id, response.id) }
        )
    }

    @Test
    @Tag(NEGATIVE_TEST)
    fun `should not allow null RevisionId`() = runTest {
        val illegalTask = Task(
            null,
            "task-id",
            emptyMap(),
            TaskStatus.DRAFT
        )
        coEvery {
            taskOutputPort.create(any())
        } returns illegalTask

        assertThrows<java.lang.IllegalArgumentException>("should require not null") {
            createTaskService.createTask()
        }
    }
}
