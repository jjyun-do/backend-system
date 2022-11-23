package com.samsung.healthcare.platform.adapter.web.project.task

import com.ninjasquad.springmockk.MockkBean
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
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
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

    @Test
    @Tag("positive")
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

        webTestClient.get()
            .uri("/api/projects/1/tasks?end_time=2022-10-21T00:00&status=DRAFT")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Map::class.java).hasSize(2).contains(testMap1, testMap2)
    }

    @Test
    @Tag("positive")
    fun `should return matching task as body`() {
        val testMap = mapOf("testTask" to "task with id test")
        coEvery { getTaskUseCase.findById("test") } returns flowOf(testMap)

        webTestClient.get()
            .uri("/api/projects/1/tasks/test")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Map::class.java).hasSize(1).contains(testMap)
    }

    @Test
    @Tag("positive")
    fun `should return ok when task created`() {
        val createTaskResponse = CreateTaskResponse(1, "testCreate")
        coEvery { createTaskUseCase.createTask() } returns createTaskResponse

        webTestClient.post()
            .uri("/api/projects/1/tasks")
            .exchange()
            .expectStatus().isOk
            .expectBody(CreateTaskResponse::class.java).isEqualTo(createTaskResponse)
    }

    @Test
    @Tag("positive")
    fun `should return no content for update`() {
        val updateTaskCommand = UpdateTaskCommand(title = "testUpdate", status = TaskStatus.DRAFT, items = emptyList())
        coJustRun { updateTaskUseCase.updateTask("test", match { it.value == 1 }, updateTaskCommand) }

        webTestClient.patch()
            .uri("/api/projects/1/tasks/test?revision_id=1")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(updateTaskCommand))
            .exchange()
            .expectStatus().isNoContent
    }
}
