package com.samsung.healthcare.platform.adapter.web.project.task

import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.platform.POSITIVE_TEST
import com.samsung.healthcare.platform.adapter.web.exception.ExceptionHandler
import com.samsung.healthcare.platform.adapter.web.filter.TenantHandlerFilterFunction
import com.samsung.healthcare.platform.adapter.web.security.SecurityConfig
import com.samsung.healthcare.platform.application.exception.GlobalErrorAttributes
import com.samsung.healthcare.platform.application.port.input.project.task.CreateTaskResponse
import com.samsung.healthcare.platform.application.port.input.project.task.CreateTaskUseCase
import com.samsung.healthcare.platform.application.port.input.project.task.GetTaskCommand
import com.samsung.healthcare.platform.application.port.input.project.task.GetTaskUseCase
import com.samsung.healthcare.platform.application.port.input.project.task.UpdateTaskCommand
import com.samsung.healthcare.platform.application.port.input.project.task.UpdateTaskUseCase
import com.samsung.healthcare.platform.enums.TaskStatus
import io.mockk.coEvery
import io.mockk.coJustRun
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@WebFluxTest
@Import(
    TaskHandler::class,
    TaskRouter::class,
    TenantHandlerFilterFunction::class,
    SecurityConfig::class,
    ExceptionHandler::class,
    GlobalErrorAttributes::class
)
internal class TaskHandlerTest {
    @MockkBean
    private lateinit var getTaskUseCase: GetTaskUseCase

    @MockkBean
    private lateinit var createTaskUseCase: CreateTaskUseCase

    @MockkBean
    private lateinit var updateTaskUseCase: UpdateTaskUseCase

    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val projectId = 1

    @Test
    @Tag(POSITIVE_TEST)
    fun `should return relevant tasks as body`() {
        val testLocalDateTime = LocalDateTime.parse("2022-10-21T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val getTaskCommand = GetTaskCommand(
            null,
            testLocalDateTime,
            null,
            "DRAFT"
        )
        val testMap1 = mapOf("test1" to "shouldBeTask")
        val testMap2 = mapOf("test2" to "shouldAlsoBeTask")
        coEvery { getTaskUseCase.findByPeriod(getTaskCommand) } returns flowOf(testMap1, testMap2)

        val result = webTestClient.get()
            .uri("/api/projects/$projectId/tasks?end_time=2022-10-21T00:00&status=DRAFT")
            .exchange()
            .expectBodyList(Map::class.java)
            .returnResult()

        assertThat(result.status).isEqualTo(HttpStatus.OK)
        assertThat(result.responseBody?.size).isEqualTo(2)
        assertThat(result.responseBody).contains(testMap1, testMap2)
    }

    @Test
    @Tag(POSITIVE_TEST)
    fun `should return matching task as body`() {
        val testMap = mapOf("testTask" to "task with id test")
        val taskId = "SAMPLE-TASK-ID"
        coEvery { getTaskUseCase.findById(taskId) } returns flowOf(testMap)

        val result = webTestClient.get()
            .uri("/api/projects/$projectId/tasks/$taskId")
            .exchange()
            .expectBodyList(Map::class.java)
            .returnResult()

        assertThat(result.status).isEqualTo(HttpStatus.OK)
        assertThat(result.responseBody?.size).isEqualTo(1)
        assertThat(result.responseBody).contains(testMap)
    }

    @Test
    @Tag(POSITIVE_TEST)
    fun `should return ok when task created`() {
        val createTaskResponse = CreateTaskResponse(1, "testCreate")
        coEvery { createTaskUseCase.createTask() } returns createTaskResponse

        val result = webTestClient.post()
            .uri("/api/projects/$projectId/tasks")
            .exchange()
            .expectBody(CreateTaskResponse::class.java)
            .returnResult()

        assertThat(result.status).isEqualTo(HttpStatus.OK)
        assertThat(result.responseBody).isEqualTo(createTaskResponse)
    }

    @Test
    @Tag(POSITIVE_TEST)
    fun `should return no content for update`() {
        val updateTaskCommand = UpdateTaskCommand(title = "testUpdate", status = TaskStatus.DRAFT, items = emptyList())
        coJustRun { updateTaskUseCase.updateTask("test", match { it.value == 1 }, updateTaskCommand) }

        val result = webTestClient.patch()
            .uri("/api/projects/$projectId/tasks/test?revision_id=1")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(updateTaskCommand))
            .exchange()
            .expectBody()
            .returnResult()

        assertThat(result.status).isEqualTo(HttpStatus.NO_CONTENT)
    }
}
