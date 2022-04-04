package com.samsung.healthcare.platform.application.port.input

import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId

interface GetProjectQuery {
    suspend fun findProjectById(id: ProjectId): Project
}
