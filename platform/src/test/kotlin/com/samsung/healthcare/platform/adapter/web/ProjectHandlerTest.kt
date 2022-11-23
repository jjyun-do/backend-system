package com.samsung.healthcare.platform.adapter.web

import com.ninjasquad.springmockk.MockkBean
import com.samsung.healthcare.account.application.port.input.GetAccountUseCase
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.platform.adapter.web.exception.ExceptionHandler
import com.samsung.healthcare.platform.adapter.web.filter.JwtAuthenticationFilterFunction
import com.samsung.healthcare.platform.adapter.web.security.SecurityConfig
import com.samsung.healthcare.platform.application.exception.GlobalErrorAttributes
import com.samsung.healthcare.platform.application.port.input.CreateProjectCommand
import com.samsung.healthcare.platform.application.port.input.CreateProjectUseCase
import com.samsung.healthcare.platform.application.port.input.GetProjectQuery
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.kotlin.core.publisher.toMono

@WebFluxTest
@Import(
    ProjectHandler::class,
    ProjectRouter::class,
    JwtAuthenticationFilterFunction::class,
    SecurityConfig::class,
    ExceptionHandler::class,
    GlobalErrorAttributes::class
)
internal class ProjectHandlerTest {
    @MockkBean
    private lateinit var createProjectUseCase: CreateProjectUseCase
    @MockkBean
    private lateinit var getProjectQuery: GetProjectQuery
    @MockkBean
    private lateinit var getAccountUseCase: GetAccountUseCase
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    @Tag("negative")
    fun `should throw unauthorized when no token`() {
        val createProjectCommand = CreateProjectCommand("newProject")
        val projectId = ProjectId.from(1)
        coEvery { createProjectUseCase.registerProject(any()) } returns projectId

        webTestClient.post()
            .uri("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(createProjectCommand))
            .exchange()
            .expectStatus().isUnauthorized
    }

    @Test
    @Tag("positive")
    fun `should return that project was created`() {
        val createProjectCommand = CreateProjectCommand("newProject")
        val projectId = ProjectId.from(1)
        coEvery { createProjectUseCase.registerProject(any()) } returns projectId

        every {
            getAccountUseCase.getAccountFromToken("adminToken")
        } returns Account(
            "testAdmin",
            Email("testAdmin@s-healthstack.com"),
            listOf(Role.TeamAdmin)
        ).toMono()

        webTestClient.post()
            .uri("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .header(AUTHORIZATION, "Bearer adminToken")
            .body(BodyInserters.fromValue(createProjectCommand))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().location("/api/projects/$projectId")
    }

    @Test
    @Tag("positive")
    fun `should return matching project as body`() {
        val projectId = ProjectId.from(1)
        val project = Project(projectId, "testProject", emptyMap(), true)
        coEvery { getProjectQuery.findProjectById(projectId) } returns project

        every {
            getAccountUseCase.getAccountFromToken("testUser")
        } returns Account(
            "testUser",
            Email("test@s-healthstack.com"),
            listOf(Role.ProjectRole.Researcher("1"))
        ).toMono()

        webTestClient.get()
            .uri("/api/projects/$projectId")
            .header(AUTHORIZATION, "Bearer testUser")
            .exchange()
            .expectStatus().isOk
            .expectBody(Project::class.java).isEqualTo(project)
    }

    @Test
    @Tag("positive")
    fun `should return all accessible projects as body`() {
        val project1 = Project(ProjectId.from(1), "project1", emptyMap(), true)
        val project2 = Project(ProjectId.from(2), "project2", emptyMap(), true)
        every {
            getAccountUseCase.getAccountFromToken("testUser")
        } returns Account(
            "testUser",
            Email("test@s-healthstack.com"),
            listOf(Role.ProjectRole.ProjectOwner("1"), Role.ProjectRole.HeadResearcher("2"))
        ).toMono()
        every { getProjectQuery.listProject() } returns flowOf(project1, project2)

        webTestClient.get()
            .uri("/api/projects")
            .header(AUTHORIZATION, "Bearer testUser")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Project::class.java).hasSize(2).contains(project1, project2)
    }
}
