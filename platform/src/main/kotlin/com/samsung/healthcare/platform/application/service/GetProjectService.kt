package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.account.domain.AccessProjectAuthority
import com.samsung.healthcare.platform.application.authorize.Authorizer
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
        Authorizer.getAccount(AccessProjectAuthority(id.value.toString()))
            .flatMap {
                mono {
                    loadProjectPort.findById(id)
                }
            }.switchIfEmpty(Mono.error(NotFoundException()))
            .awaitSingle()

    override fun listProject(): Flow<Project> =
        Authorizer.getAccessibleProjects()
            .flatMapMany { projectIds ->
                loadProjectPort.findProjectByIdIn(projectIds)
                    .asFlux()
            }.asFlow()
}
