package com.samsung.healthcare.platform.adapter.persistence.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.samsung.healthcare.platform.adapter.persistence.converter.JsonReadConverter
import com.samsung.healthcare.platform.adapter.persistence.converter.JsonWriteConverter
import com.samsung.healthcare.platform.application.config.ApplicationProperties
import com.samsung.healthcare.platform.application.config.ConnectionSpecs
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.File

@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
class ReactivePostgresConfig(
    private val objectMapper: ObjectMapper,
    private val config: ApplicationProperties,
) : AbstractR2dbcConfiguration() {
    val multiTenantRoutingConnectionFactory = MultiTenantRoutingConnectionFactory()
    private val yamlMapper: ObjectMapper = ObjectMapper(YAMLFactory()).findAndRegisterModules()

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        multiTenantRoutingConnectionFactory.setLenientFallback(false)
        val connectionSpecs = yamlMapper.readValue(File(config.multiTenant.path), ConnectionSpecs::class.java).specs

        connectionSpecs["DEFAULT"]?.let {
            multiTenantRoutingConnectionFactory.setDefaultTargetConnectionFactory(
                multiTenantRoutingConnectionFactory.buildPostgresqlConnectionFactory(it)
            )
        }
        multiTenantRoutingConnectionFactory.setTargetConnectionFactories(tenants())

        multiTenantRoutingConnectionFactory.afterPropertiesSet()

        return multiTenantRoutingConnectionFactory
    }

    fun tenants(): MutableMap<String, ConnectionFactory> {
        val ret: MutableMap<String, ConnectionFactory> = mutableMapOf()

        val connectionSpecs = yamlMapper.readValue(File(config.multiTenant.path), ConnectionSpecs::class.java).specs
        for (entry in connectionSpecs) {
            ret[entry.key] = multiTenantRoutingConnectionFactory.buildPostgresqlConnectionFactory(entry.value)
        }

        return ret
    }

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
