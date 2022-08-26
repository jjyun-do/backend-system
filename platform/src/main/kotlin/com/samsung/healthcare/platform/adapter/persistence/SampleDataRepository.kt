package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.healthdata.HealthDataEntity
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface SampleDataRepository<T : HealthDataEntity> : HealthDataRepository<T>
