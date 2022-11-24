package com.samsung.healthcare.dataqueryservice.adapter.web

import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.dataqueryservice.adapter.web.interceptor.JwtAuthenticationInterceptor
import com.samsung.healthcare.dataqueryservice.application.port.input.QueryDataResultSet
import com.samsung.healthcare.dataqueryservice.application.service.QueryDataService
import io.mockk.every
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.time.Instant

@ExtendWith(SpringExtension::class)
@WebMvcTest(DataQueryController::class)
@AutoConfigureMockMvc(addFilters = false)
@Import(
    JwtAuthenticationInterceptor::class
)
internal class DataQueryControllerTest {
    @MockkBean
    private lateinit var queryDataService: QueryDataService

    @MockkBean
    private lateinit var getAccountService: GetAccountUseCase

    @MockkBean
    private lateinit var jwtDecoder: JwtDecoder

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    @Tag("positive")
    fun `controller should work properly`() {

        every { queryDataService.execute(any(), any(), any()) } returns QueryDataResultSet(
            QueryDataResultSet.MetaData(
                emptyList(),
                0
            ),
            emptyList()
        )

        every { jwtDecoder.decode(any()) } returns Jwt(
            "token",
            Instant.now(),
            Instant.now().plusSeconds(86_400),
            mapOf(Pair("alg", "RS256")),
            mapOf(
                Pair("sub", "random-uuid"),
                Pair("email", "test@research-hub.test.com"),
                Pair("roles", listOf(Role.ProjectRole.HeadResearcher("1").roleName))
            )
        )

        webClient.post().uri("/api/projects/project-id/sql")
            .contentType(MediaType.APPLICATION_JSON)
            .header(AUTHORIZATION, "Bearer some_token")
            .body(BodyInserters.fromValue(TestRequest("sql-string")))
            .exchange()
            .expectStatus()
            .isOk
    }

    @Test
    @Tag("negative")
    fun `controller should throw UnauthorizedException when authorization is failed`() {

        every { jwtDecoder.decode(any()) } returns Jwt(
            "token",
            Instant.now(),
            Instant.now().plusSeconds(86_400),
            mapOf(Pair("alg", "RS256")),
            mapOf(
                Pair("sub", "random-uuid"),
                Pair("email", "test@research-hub.test.com"),
                Pair("roles", listOf(Role.ProjectRole.HeadResearcher("1").roleName))
            )
        )

        webClient.post().uri("/api/projects/project-id/sql")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(TestRequest("sql-string")))
            .exchange()
            .expectStatus()
            .isUnauthorized
    }

    @Test
    @Tag("negative")
    fun `controller should throw BadJwtException when JWT is invalid`() {

        every { jwtDecoder.decode(any()) } returns Jwt(
            "token",
            Instant.now(),
            Instant.now().plusSeconds(86_400),
            mapOf(Pair("alg", "RS256")),
            mapOf(
                Pair("subject", "random-uuid"),
                Pair("email", "test@research-hub.test.com"),
                Pair("roles", listOf(Role.ProjectRole.HeadResearcher("1").roleName))
            )
        )

        webClient.post().uri("/api/projects/project-id/sql")
            .contentType(MediaType.APPLICATION_JSON)
            .header(AUTHORIZATION, "Bearer some_token")
            .body(BodyInserters.fromValue(TestRequest("sql-string")))
            .exchange()
            .expectStatus()
            .isBadRequest
    }

    private data class TestRequest(val sql: String)
}

internal fun WebTestClient.post(path: String, param: Any) =
    this.post().uri(path).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(param)).exchange()
