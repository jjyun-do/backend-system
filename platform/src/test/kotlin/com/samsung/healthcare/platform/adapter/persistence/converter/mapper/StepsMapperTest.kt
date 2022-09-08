package com.samsung.healthcare.platform.adapter.persistence.converter.mapper

import com.samsung.healthcare.platform.adapter.persistence.converter.mapper.sub.StepsMapper
import com.samsung.healthcare.platform.domain.project.UserProfile.UserId
import com.samsung.healthcare.platform.domain.healthdata.HealthData.HealthDataId
import com.samsung.healthcare.platform.domain.healthdata.Steps
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

internal class StepsMapperTest {
    @Test
    fun `should convert domain to entity`() {
        val startTime = LocalDateTime.of(2022, 8, 7, 12, 0, 0)
        val endTime = LocalDateTime.of(2022, 9, 7, 12, 0, 0)
        val steps = Steps(
            HealthDataId.from(1),
            startTime.toInstant(ZoneOffset.UTC),
            endTime.toInstant(ZoneOffset.UTC),
            10000
        )
        val userId = UserId.from("jjyun.do")

        val stepsEntity = StepsMapper.INSTANCE.toEntity(steps, userId)

        assertThat(stepsEntity.id).isEqualTo(steps.id?.value)
        assertThat(stepsEntity.startTime).isEqualTo(
            LocalDateTime.ofInstant(
                steps.startTime,
                ZoneOffset.UTC
            )
        )
        assertThat(stepsEntity.endTime).isEqualTo(
            LocalDateTime.ofInstant(
                steps.endTime,
                ZoneOffset.UTC
            )
        )
        assertThat(stepsEntity.userId).isEqualTo(userId.value)
        assertThat(stepsEntity.count).isEqualTo(steps.count)
    }
}
