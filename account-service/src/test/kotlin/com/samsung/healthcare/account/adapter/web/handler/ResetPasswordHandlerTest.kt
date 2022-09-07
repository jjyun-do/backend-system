package com.samsung.healthcare.account.adapter.web.handler

import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.account.adapter.web.config.SecurityConfig
import com.samsung.healthcare.account.adapter.web.exception.GlobalErrorAttributes
import com.samsung.healthcare.account.adapter.web.exception.GlobalExceptionHandler
import com.samsung.healthcare.account.adapter.web.filter.JwtTokenAuthenticationFilter
import com.samsung.healthcare.account.adapter.web.router.RESET_PASSWORD_PATH
import com.samsung.healthcare.account.adapter.web.router.ResetPasswordRouter
import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import com.samsung.healthcare.account.application.port.input.ResetPasswordCommand
import com.samsung.healthcare.account.application.port.input.ResetPasswordUseCase
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest
@Import(
    ResetPasswordHandler::class,
    ResetPasswordRouter::class,
    GlobalExceptionHandler::class,
    GlobalErrorAttributes::class,
    JwtTokenAuthenticationFilter::class,
    SecurityConfig::class,
)
internal class ResetPasswordHandlerTest {

    @MockkBean
    private lateinit var resetPasswordService: ResetPasswordUseCase

    @MockkBean
    private lateinit var getAccountService: GetAccountUseCase

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `should return ok`() {
        val resetPasswordCommand = ResetPasswordCommand("reset-token", "new-pw")
        every { resetPasswordService.resetPassword(resetPasswordCommand) } returns Mono.empty()

        webClient.post(RESET_PASSWORD_PATH, resetPasswordCommand)
            .expectStatus()
            .isOk
    }

    @Test
    fun `should return bad request when reset token is not given`() {
        webClient.post(RESET_PASSWORD_PATH, ResetPasswordRequest(null, "new-pw"))
            .expectStatus()
            .isBadRequest
    }

    @Test
    fun `should return bad request when password is not given`() {
        webClient.post(RESET_PASSWORD_PATH, ResetPasswordRequest("token", null))
            .expectStatus()
            .isBadRequest
    }

    private data class ResetPasswordRequest(
        val resetToken: String?,
        val password: String?
    )
}
