package com.samsung.healthcare.account.adapter.web.handler

import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.account.POSITIVE_TEST
import com.samsung.healthcare.account.adapter.web.config.SecurityConfig
import com.samsung.healthcare.account.adapter.web.exception.GlobalErrorAttributes
import com.samsung.healthcare.account.adapter.web.exception.GlobalExceptionHandler
import com.samsung.healthcare.account.adapter.web.filter.JwtTokenAuthenticationFilter
import com.samsung.healthcare.account.adapter.web.router.LIST_USER_PATH
import com.samsung.healthcare.account.adapter.web.router.ListUserRouter
import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import com.samsung.healthcare.account.application.service.ListUserService
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.account.domain.RoleFactory
import io.mockk.every
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest
@Import(
    ListUserHandler::class,
    ListUserRouter::class,
    GlobalExceptionHandler::class,
    GlobalErrorAttributes::class,
    JwtTokenAuthenticationFilter::class,
    SecurityConfig::class,
)
internal class ListUserHandlerTest {
    @MockkBean
    private lateinit var listUserService: ListUserService

    @MockkBean
    private lateinit var getAccountService: GetAccountUseCase

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    @Tag(POSITIVE_TEST)
    fun `list users should return ok`() {
        val role = RoleFactory.createRole(Role.TEAM_ADMIN)
        val account = Account("account-id", Email("test@research-hub.test.com"), listOf(role))
        every { listUserService.listAllUsers() } returns Mono.just(listOf(account))

        webClient.get(LIST_USER_PATH).expectStatus().isOk
    }
}
