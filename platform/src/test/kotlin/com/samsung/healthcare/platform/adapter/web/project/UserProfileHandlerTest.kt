package com.samsung.healthcare.platform.adapter.web.project

import com.google.firebase.auth.FirebaseAuth
import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.platform.adapter.web.exception.ExceptionHandler
import com.samsung.healthcare.platform.adapter.web.filter.IdTokenFilterFunction
import com.samsung.healthcare.platform.adapter.web.filter.TenantHandlerFilterFunction
import com.samsung.healthcare.platform.adapter.web.security.SecurityConfig
import com.samsung.healthcare.platform.application.exception.GlobalErrorAttributes
import com.samsung.healthcare.platform.application.port.input.CreateUserCommand
import com.samsung.healthcare.platform.application.port.input.project.UserProfileInputPort
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
    UserProfileHandler::class,
    UserProfileRouter::class,
    IdTokenFilterFunction::class,
    TenantHandlerFilterFunction::class,
    SecurityConfig::class,
    ExceptionHandler::class,
    GlobalErrorAttributes::class
)
internal class UserProfileHandlerTest {
    @MockkBean
    private lateinit var userProfileInputPort: UserProfileInputPort
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Tag("positive")
    fun `should return ok`() {
        mockkStatic(FirebaseAuth::class)
        every { FirebaseAuth.getInstance().verifyIdToken(any()) } returns mockk(relaxed = true)
        val createUserCommand = CreateUserCommand("testUID", emptyMap())
        coJustRun { userProfileInputPort.registerUser(createUserCommand) }

        webTestClient.post()
            .uri("/api/projects/1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header("id-token", "testToken")
            .body(BodyInserters.fromValue(createUserCommand))
            .exchange()
            .expectStatus().isOk
    }
}
