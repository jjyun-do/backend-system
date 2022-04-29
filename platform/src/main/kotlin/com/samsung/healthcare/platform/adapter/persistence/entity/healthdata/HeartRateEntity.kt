package com.samsung.healthcare.platform.adapter.persistence.entity.healthdata

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
        TODO("Not yet implemented")
    }
}

fun HeartRate.toEntity(): HeartRateEntity =
    HeartRateEntity(
        userId = this.userId.value,
        time = this.time,
        bpm = this.bpm,
    )
