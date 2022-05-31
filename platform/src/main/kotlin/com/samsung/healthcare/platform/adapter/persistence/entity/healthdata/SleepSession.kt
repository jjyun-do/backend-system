package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

import com.samsung.healthcare.platform.domain.healthdata.SleepSession
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("sleepsessions")
data class SleepSessionEntity(
    override val id: Int? = null,
    override val userId: String,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    val title: String? = null,
    val notes: String? = null,
) : IntervalDataEntity(id, userId, startTime, endTime) {
    override fun toDomain(): SleepSession {
        TODO("Not yet implemented")
    }
}

fun SleepSession.toEntity(): SleepSessionEntity =
    SleepSessionEntity(
        userId = this.userId.value,
        startTime = this.startTime,
        endTime = this.endTime,
        title = this.title,
        notes = this.notes,
    )
