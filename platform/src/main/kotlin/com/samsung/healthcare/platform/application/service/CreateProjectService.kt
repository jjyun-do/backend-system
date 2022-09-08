package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.CreateStudyAuthority
import com.samsung.healthcare.platform.application.exception.UnauthorizedException
import com.samsung.healthcare.platform.application.port.input.CreateProjectCommand
import com.samsung.healthcare.platform.application.port.input.CreateProjectUseCase
import com.samsung.healthcare.platform.application.port.output.CreateProjectPort
import com.samsung.healthcare.platform.application.port.output.CreateProjectRolePort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateProjectService(
    private val createProjectPort: CreateProjectPort,
    private val createProjectRolePort: CreateProjectRolePort
) : CreateProjectUseCase {

    override suspend fun registerProject(command: CreateProjectCommand): ProjectId =
        ContextHolder.getAccount()
            .filter { it.hasPermission(CreateStudyAuthority) }
            .switchIfEmpty(Mono.error(UnauthorizedException()))
            .flatMap { account ->
                createProject(account, command)
            }.awaitSingle()

    private fun createProject(account: Account, command: CreateProjectCommand): Mono<ProjectId> =
        mono {
            createProjectPort.create(
                Project.newProject(command.name, command.info, command.isOpen)
            )
        }.flatMap { projectId ->
            createProjectRolePort.createProjectRoles(account, projectId)
                .then(Mono.just(projectId))
        }.doOnError { it.printStackTrace() }
}
