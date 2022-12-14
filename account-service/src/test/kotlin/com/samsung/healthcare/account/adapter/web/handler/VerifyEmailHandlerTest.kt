package com.samsung.healthcare.account.adapter.web.handler

import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.account.POSITIVE_TEST
import com.samsung.healthcare.account.adapter.web.config.SecurityConfig
import com.samsung.healthcare.account.adapter.web.exception.GlobalErrorAttributes
import com.samsung.healthcare.account.adapter.web.exception.GlobalExceptionHandler
import com.samsung.healthcare.account.adapter.web.router.VERIFY_EMAIL_PATH
import com.samsung.healthcare.account.adapter.web.router.VerifyEmailRouter
import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import com.samsung.healthcare.account.application.service.VerifyEmailService
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest
@Import(
    VerifyEmailHandler::class,
    VerifyEmailRouter::class,
    GlobalExceptionHandler::class,
    GlobalErrorAttributes::class,
    SecurityConfig::class,
)
internal class VerifyEmailHandlerTest {
    @MockkBean
    private lateinit var verifyEmailService: VerifyEmailService

    @MockkBean
    private lateinit var getAccountService: GetAccountUseCase

    @Autowired
    private lateinit var webClient: WebTestClient

    private val token = "test-token"

    @Test
    @Tag(POSITIVE_TEST)
    fun `should return ok`() {
        every { verifyEmailService.verifyEmail(token) } returns Mono.empty()

        val result = webClient.post(VERIFY_EMAIL_PATH, TestRequest(token))
            .expectBody()
            .returnResult()

        assertThat(result.status).isEqualTo(HttpStatus.OK)
    }

    private data class TestRequest(val token: String)
}
