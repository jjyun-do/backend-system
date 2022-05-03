package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

import com.samsung.healthcare.platform.domain.healthdata.SleepStage
import com.samsung.healthcare.platform.domain.healthdata.Steps
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("sleepstage")
data class SleepStageEntity(
    override val id: Int? = null,
    override val userId: String,
    override val startTime: LocalDateTime,
    override val endTime: LocalDateTime,
    val stage: String,
) : IntervalDataEntity(id, userId, startTime, endTime) {
    override fun toDomain(): Steps {
        TODO("Not yet implemented")
    }
}

fun SleepStage.toEntity(): SleepStageEntity =
    SleepStageEntity(
        userId = this.userId.value,
        startTime = this.startTime,
        endTime = this.endTime,
        stage = this.stage,
    )
