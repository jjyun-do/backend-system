package com.samsung.healthcare.platform.adapter.persistence.entity

import com.samsung.healthcare.platform.domain.datatype.HeartRate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("heartrates")
data class HeartRateEntity(
    @Id
    val id: Int?,
    val userId: Int,
    val time: LocalDateTime,
    val bpm: Long,
) {
    fun toDomain(): HeartRate {
        TODO("Not yet implemented")
    }
}

fun HeartRate.toEntity(): HeartRateEntity {
    TODO("Not yet implemented")
}
