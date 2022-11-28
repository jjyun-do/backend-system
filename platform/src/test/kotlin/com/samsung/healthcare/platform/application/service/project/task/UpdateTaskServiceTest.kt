package com.samsung.healthcare.platform.application.service.project.task

import com.samsung.healthcare.platform.POSITIVE_TEST
import com.samsung.healthcare.platform.application.port.input.project.task.UpdateTaskCommand
import com.samsung.healthcare.platform.application.port.output.project.task.ItemOutputPort
import com.samsung.healthcare.platform.application.port.output.project.task.TaskOutputPort
import com.samsung.healthcare.platform.domain.project.task.Item
import com.samsung.healthcare.platform.domain.project.task.RevisionId
import com.samsung.healthcare.platform.domain.project.task.Task
import com.samsung.healthcare.platform.enums.ItemType
import com.samsung.healthcare.platform.enums.TaskStatus
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class UpdateTaskServiceTest {
    private val taskOutputPort = mockk<TaskOutputPort>()
    private val itemOutputPort = mockk<ItemOutputPort>()
    private val updateTaskService = UpdateTaskService(
        taskOutputPort,
        itemOutputPort
    )

    @Test
    @Tag(POSITIVE_TEST)
    fun `should not update publishedAt`() = runTest {
        val revisionId = RevisionId.from(1)
        val updateTaskCommand = UpdateTaskCommand(
            title = "not yet published",
            description = "updating without publishing",
            status = TaskStatus.DRAFT,
            items = emptyList()
        )
        val task = Task(
            revisionId,
            "test-task",
            mapOf(
                "title" to "not yet published",
                "description" to "updating without publishing"
            ),
            TaskStatus.DRAFT
        )

        coEvery { taskOutputPort.update(task) } returns task

        coJustRun { itemOutputPort.update(1, emptyList()) }

        updateTaskService.updateTask(
            "test-task",
            revisionId,
            updateTaskCommand
        )

        coVerify { taskOutputPort.update(task) }
    }

    @Test
    @Tag(POSITIVE_TEST)
    fun `should default endTime to 3mo after startTime if no value provided`() = runTest {
        val revisionId = RevisionId.from(1)
        val updateTaskCommand = UpdateTaskCommand(
            title = "endTime test",
            description = "should default to 3 months later",
            schedule = "weekly",
            startTime = LocalDateTime.parse("2022-01-20T10:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            validTime = 12,
            status = TaskStatus.PUBLISHED,
            items = listOf(
                UpdateTaskCommand.UpdateItemCommand(mapOf("test item 0" to "blah"), ItemType.QUESTION, 0),
                UpdateTaskCommand.UpdateItemCommand(mapOf("test item 1" to "blep"), ItemType.QUESTION, 1)
            )
        )
        mockkStatic(LocalDateTime::class)
        val testLocalDateTime = LocalDateTime.parse("2022-10-21T17:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        every { LocalDateTime.now() } returns testLocalDateTime
        val task = Task(
            revisionId,
            "test-task",
            mapOf(
                "title" to "endTime test",
                "description" to "should default to 3 months later",
                "schedule" to "weekly",
                "startTime" to LocalDateTime.parse("2022-01-20T10:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "endTime" to LocalDateTime.parse("2022-04-20T10:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                "validTime" to 12
            ),
            TaskStatus.PUBLISHED,
            publishedAt = LocalDateTime.now()
        )
        val item1 = Item(
            null,
            revisionId,
            "test-task",
            "Question0",
            mapOf("test item 0" to "blah"),
            ItemType.QUESTION,
            0
        )
        val item2 = Item(
            null,
            revisionId,
            "test-task",
            "Question1",
            mapOf("test item 1" to "blep"),
            ItemType.QUESTION,
            1
        )

        coEvery { taskOutputPort.update(task) } returns task
        coJustRun { itemOutputPort.update(1, listOf(item1, item2)) }

        updateTaskService.updateTask("test-task", revisionId, updateTaskCommand)

        coVerifyOrder {
            taskOutputPort.update(task)
            itemOutputPort.update(1, listOf(item1, item2))
        }
    }
}
