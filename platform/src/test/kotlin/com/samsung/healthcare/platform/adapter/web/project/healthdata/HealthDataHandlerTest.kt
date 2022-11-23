package com.samsung.healthcare.platform.adapter.web.project.healthdata

import com.google.firebase.auth.FirebaseAuth
import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.platform.adapter.web.exception.ExceptionHandler
import com.samsung.healthcare.platform.adapter.web.filter.IdTokenFilterFunction
import com.samsung.healthcare.platform.adapter.web.filter.TenantHandlerFilterFunction
import com.samsung.healthcare.platform.adapter.web.security.SecurityConfig
import com.samsung.healthcare.platform.application.exception.GlobalErrorAttributes
import com.samsung.healthcare.platform.application.port.input.project.UpdateUserProfileLastSyncedTimeUseCase
import com.samsung.healthcare.platform.application.port.input.project.healthdata.SaveHealthDataCommand
import com.samsung.healthcare.platform.application.port.input.project.healthdata.SaveHealthDataUseCase
import com.samsung.healthcare.platform.domain.project.healthdata.HealthData
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@WebFluxTest
@Import(
    HealthDataHandler::class,
    HealthDataRouter::class,
    IdTokenFilterFunction::class,
    TenantHandlerFilterFunction::class,
    SecurityConfig::class,
    ExceptionHandler::class,
    GlobalErrorAttributes::class
)
internal class HealthDataHandlerTest {
    @MockkBean
    private lateinit var saveHealthDataUseCase: SaveHealthDataUseCase
    @MockkBean
    private lateinit var updateUserProfileLastSyncedTimeUseCase: UpdateUserProfileLastSyncedTimeUseCase
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Tag("positive")
    fun `should return accepted`() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance().verifyIdToken(any()) } returns mockk(relaxed = true)
        val saveHealthDataCommand = SaveHealthDataCommand(HealthData.HealthDataType.HEART_RATE, emptyList())
        coJustRun { updateUserProfileLastSyncedTimeUseCase.updateLastSyncedTime(any()) }
        coJustRun { saveHealthDataUseCase.saveHealthData(any(), saveHealthDataCommand) }

        webTestClient.post()
            .uri("/api/projects/1/health-data")
            .contentType(MediaType.APPLICATION_JSON)
            .header("id-token", "testToken")
            .body(BodyInserters.fromValue(saveHealthDataCommand))
            .exchange()
            .expectStatus().isAccepted
    }
}
