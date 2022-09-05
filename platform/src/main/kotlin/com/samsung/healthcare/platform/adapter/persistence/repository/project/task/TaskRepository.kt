package com.samsung.healthcare.platform.adapter.persistence.repository.project.task

import com.samsung.healthcare.platform.adapter.persistence.entity.project.task.TaskEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TaskRepository : CoroutineCrudRepository<TaskEntity, Int>
