package com.samsung.healthcare.platform.application.port.input.project.task

import com.samsung.healthcare.platform.enums.ItemType
import com.samsung.healthcare.platform.enums.TaskStatus
import java.time.LocalDateTime

data class UpdateTaskCommand(
    val title: String,
    val description: String? = null,
    val schedule: String? = null,
    val startTime: LocalDateTime? = null,
    var endTime: LocalDateTime? = null,
    val validTime: Int? = null,
    val status: TaskStatus,
    val items: List<UpdateItemCommand>,
    val condition: Map<String, Any> = emptyMap(),
) {
    companion object {
        private const val DEFAULT_END_TIME_MONTH: Long = 3
    }

    // TODO: validate
    init {
        if (status == TaskStatus.PUBLISHED) {
            requireNotNull(schedule)
            requireNotNull(startTime)
            requireNotNull(validTime)
            if (endTime == null)
                endTime = startTime.plusMonths(DEFAULT_END_TIME_MONTH)
        }
    }

    data class UpdateItemCommand(
        val contents: Map<String, Any>,
        val type: ItemType,
        val sequence: Int?,
    ) {
        init {
            requireNotNull(sequence)
            try {
                requireValidContents()
            } catch (e: NullPointerException) {
                throw IllegalArgumentException()
            } catch (e: ClassCastException) {
                throw IllegalArgumentException()
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun requireValidContents() {
            when (this.type) {
                ItemType.QUESTION -> {
                    when (contents["type"]) {
                        "CHOICE" -> {
                            require(contents.containsKey("title"))
                            val props = contents["properties"] as Map<*, *>
                            requireNotNull(props["tag"])
                            require(listOf("RADIO", "CHECKBOX", "DROPDOWN").contains(props["tag"]))
                            require(
                                (props["options"] as List<Map<*, *>>).all {
                                    it.containsKey("value")
                                }
                            )
                        }

                        else -> require(false)
                    }
                }

                else -> require(false)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    val properties = mapOf<String, Any?>(
        "title" to title,
        "description" to description,
        "schedule" to schedule,
        "startTime" to startTime,
        "endTime" to endTime,
        "validTime" to validTime
    ).filterValues { it != null } as Map<String, Any>
}
