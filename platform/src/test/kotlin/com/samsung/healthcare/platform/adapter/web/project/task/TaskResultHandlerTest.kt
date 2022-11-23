package com.samsung.healthcare.platform.adapter.web.project.task

import com.google.firebase.auth.FirebaseAuth
import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.platform.adapter.web.exception.ExceptionHandler
import com.samsung.healthcare.platform.adapter.web.filter.IdTokenFilterFunction
import com.samsung.healthcare.platform.adapter.web.filter.TenantHandlerFilterFunction
import com.samsung.healthcare.platform.adapter.web.security.SecurityConfig
import com.samsung.healthcare.platform.application.exception.GlobalErrorAttributes
import com.samsung.healthcare.platform.application.port.input.project.UpdateUserProfileLastSyncedTimeUseCase
import com.samsung.healthcare.platform.application.port.input.project.task.UploadTaskResultCommand
import com.samsung.healthcare.platform.application.port.input.project.task.UploadTaskResultUseCase
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import

import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.LocalDateTime

@WebFluxTest
@Import(
    TaskResultHandler::class,
    TaskResultRouter::class,
    IdTokenFilterFunction::class,
    TenantHandlerFilterFunction::class,
    SecurityConfig::class,
    ExceptionHandler::class,
    GlobalErrorAttributes::class
)
internal class TaskResultHandlerTest {
    @MockkBean
    private lateinit var uploadTaskResultUseCase: UploadTaskResultUseCase
    @MockkBean
    private lateinit var updateUserProfileLastSyncedTimeUseCase: UpdateUserProfileLastSyncedTimeUseCase
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Tag("positive")
    fun `should return ok`() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance().verifyIdToken(any()) } returns mockk(relaxed = true)
        val taskResult1 = UploadTaskResultCommand(
            1,
            "testTask",
            "user1",
            LocalDateTime.now().minusHours(5),
            LocalDateTime.now(),
            emptyList()
        )
        val taskResult2 = UploadTaskResultCommand(
            1,
            "testTask",
            "user2",
            LocalDateTime.now().minusHours(6),
            LocalDateTime.now().minusHours(2),
            emptyList()
        )
        val uploadCommandList = listOf(taskResult1, taskResult2)
        coJustRun { updateUserProfileLastSyncedTimeUseCase.updateLastSyncedTime(any()) }
        coJustRun { uploadTaskResultUseCase.uploadResults(uploadCommandList) }

        webTestClient.patch()
            .uri("/api/projects/1/tasks")
            .header("id-token", "testToken")
            .body(BodyInserters.fromValue(uploadCommandList))
            .exchange()
            .expectStatus().isOk
    }
}
