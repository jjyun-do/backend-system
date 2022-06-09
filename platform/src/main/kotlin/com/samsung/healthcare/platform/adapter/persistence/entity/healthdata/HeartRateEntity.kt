package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.healthdata.HealthData
import com.samsung.healthcare.platform.domain.healthdata.HeartRate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("heartrates")
data class HeartRateEntity(
    override val id: Int? = null,
    override val userId: String,
    override val time: LocalDateTime,
    val bpm: Long,
) : SampleDataEntity(id, userId, time) {
    override fun toDomain(): HeartRate {
        requireNotNull(this.id)
        return HeartRate(
            HealthData.HealthDataId.from(this.id), User.UserId.from(this.userId), this.time, this.bpm
        )
    }
}

fun HeartRate.toEntity(): HeartRateEntity =
    HeartRateEntity(
        userId = this.userId.value,
        time = this.time,
        bpm = this.bpm,
    )
