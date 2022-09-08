package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.domain.Role.ProjectRole
import com.samsung.healthcare.platform.application.exception.NotFoundException
import com.samsung.healthcare.platform.application.port.input.GetProjectQuery
import com.samsung.healthcare.platform.application.port.output.LoadProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetProjectService(
    private val loadProjectPort: LoadProjectPort
) : GetProjectQuery {
    override suspend fun findProjectById(id: ProjectId): Project =
        ContextHolder.getAccount()
            .filter { account ->
                account.canAccessProject(id.value.toString())
            }.switchIfEmpty(Mono.error(NotFoundException()))
            .flatMap {
                mono {
                    loadProjectPort.findById(id)
                }
            }.switchIfEmpty(Mono.error(NotFoundException()))
            .awaitSingle()

    override fun listProject(): Flow<Project> =
        ContextHolder.getAccount()
            .map { account ->
                account.roles
                    .filterIsInstance<ProjectRole>()
                    .map { ProjectId.from(it.projectId.toInt()) }
            }.flatMapMany { projectIds ->
                loadProjectPort.findProjectByIdIn(projectIds)
                    .asFlux()
            }.asFlow()
}
