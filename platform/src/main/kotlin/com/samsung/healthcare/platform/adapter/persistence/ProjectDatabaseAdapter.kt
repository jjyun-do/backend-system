package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.toEntity
import com.samsung.healthcare.platform.application.port.output.CreateProjectPort
import com.samsung.healthcare.platform.application.port.output.LoadProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import org.springframework.stereotype.Component

@Component
class ProjectDatabaseAdapter(
    private val projectRepository: ProjectRepository
) : CreateProjectPort, LoadProjectPort {
    override suspend fun create(project: Project): ProjectId =
        ProjectId.from(
            projectRepository.save(project.toEntity()).id
        )

    override suspend fun findById(id: ProjectId): Project? =
        projectRepository.findById(id.value)
            ?.toDomain()
}
