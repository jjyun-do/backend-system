package com.samsung.healthcare.platform.adapter.persistence.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.samsung.healthcare.platform.adapter.persistence.converter.JsonReadConverter
import com.samsung.healthcare.platform.adapter.persistence.converter.JsonWriteConverter
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class ReactivePostgresConfig constructor(
    private val connectionFactory: ConnectionFactory,
    private val objectMapper: ObjectMapper
) : AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory = connectionFactory

    override fun getCustomConverters(): MutableList<Any> = mutableListOf(
        JsonReadConverter(objectMapper),
        JsonWriteConverter(objectMapper),
    )
}
