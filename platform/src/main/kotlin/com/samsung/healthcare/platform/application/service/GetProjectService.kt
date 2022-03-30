package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.GetProjectQuery
import com.samsung.healthcare.platform.application.port.output.LoadProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import org.springframework.stereotype.Service

@Service
class GetProjectService(
    private val loadProjectPort: LoadProjectPort
) : GetProjectQuery {
    override suspend fun findProject(id: ProjectId): Project? =
        loadProjectPort.loadProject(id)
}
