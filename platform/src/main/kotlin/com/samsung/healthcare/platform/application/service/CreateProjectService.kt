package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.domain.CreateStudyAuthority
import com.samsung.healthcare.platform.application.exception.UnauthorizedException
import com.samsung.healthcare.platform.application.port.input.CreateProjectCommand
import com.samsung.healthcare.platform.application.port.input.CreateProjectUseCase
import com.samsung.healthcare.platform.application.port.output.CreateProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateProjectService(
    private val createProjectPort: CreateProjectPort,
) : CreateProjectUseCase {

    override suspend fun registerProject(command: CreateProjectCommand): ProjectId =
        ContextHolder.getAccount()
            .filter { it.hasPermission(CreateStudyAuthority) }
            .switchIfEmpty(Mono.error(UnauthorizedException()))
            .then(
                mono {
                    createProjectPort.create(
                        Project.newProject(command.name, command.info, command.isOpen)
                    )
                }
            ).awaitSingle()
}
