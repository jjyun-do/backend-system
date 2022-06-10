package com.samsung.healthcare.platform.adapter.persistence.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.samsung.healthcare.platform.adapter.persistence.converter.JsonReadConverter
import com.samsung.healthcare.platform.adapter.persistence.converter.JsonWriteConverter
import com.samsung.healthcare.platform.application.config.ApplicationProperties
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class ReactivePostgresConfig(
    private val connectionFactory: ConnectionFactory,
    private val objectMapper: ObjectMapper,
    private val config: ApplicationProperties
) : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory = connectionFactory

    override fun getCustomConverters(): MutableList<Any> = mutableListOf(
        JsonReadConverter(objectMapper),
        JsonWriteConverter(objectMapper),
    )

    @Bean
    fun connectionFactoryOptionsBuilder(): ConnectionFactoryOptions.Builder =
        ConnectionFactoryOptions.parse("r2dbc:${config.db.url}")
            .mutate()
            .option(ConnectionFactoryOptions.USER, config.db.user)
            .option(ConnectionFactoryOptions.PASSWORD, config.db.password)
}
