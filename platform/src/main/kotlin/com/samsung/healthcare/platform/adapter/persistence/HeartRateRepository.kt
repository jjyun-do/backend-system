package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.entity.HeartRateEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface HeartRateRepository : CoroutineCrudRepository<HeartRateEntity, Int>
