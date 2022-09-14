package com.samsung.healthcare.platform.application.port.input.project.task

import com.samsung.healthcare.platform.enums.ItemType
import com.samsung.healthcare.platform.enums.TaskStatus
import java.time.LocalDateTime

data class UpdateTaskCommand(
    val title: String,
    val description: String,
    val schedule: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val validTime: Int,
    val status: TaskStatus,
    val items: List<UpdateItemCommand>,
    val condition: Map<String, Any> = emptyMap(),
) {
    // TODO: validate
    data class UpdateItemCommand(
        val contents: Map<String, Any>,
        val type: ItemType,
        val sequence: Int
    )

    val properties = mapOf<String, Any>(
        "title" to title,
        "description" to description,
        "schedule" to schedule,
        "startTime" to startTime,
        "endTime" to endTime,
        "validTime" to validTime
    )
}
