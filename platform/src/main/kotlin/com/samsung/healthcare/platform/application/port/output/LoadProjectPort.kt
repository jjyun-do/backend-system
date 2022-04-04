package com.samsung.healthcare.platform.application.port.output

import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId

interface LoadProjectPort {
    suspend fun findById(id: ProjectId): Project?
}
