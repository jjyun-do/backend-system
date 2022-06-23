package com.samsung.healthcare.platform.adapter.persistence.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.samsung.healthcare.platform.adapter.web.context.ContextHolder
import com.samsung.healthcare.platform.application.config.ConnectionSpec
import com.samsung.healthcare.platform.application.config.ConnectionSpecs
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.io.File

class MultiTenantRoutingConnectionFactory : AbstractRoutingConnectionFactory() {
    private val resolvedConnectionFactories: HashMap<Any, ConnectionFactory> = hashMapOf()
    private val yamlMapper: ObjectMapper = ObjectMapper(
        YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
    ).findAndRegisterModules()

    override fun determineCurrentLookupKey(): Mono<Any> =
        Mono.just(ContextHolder.getProjectId())

    fun buildPostgresqlConnectionFactory(connectionSpec: ConnectionSpec): PostgresqlConnectionFactory {
        return PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(connectionSpec.host)
                .port(connectionSpec.port)
                .database(connectionSpec.db)
                .schema(connectionSpec.schema)
                .username(connectionSpec.username)
                .password(connectionSpec.password)
                .build()
        )
    }

    override fun determineTargetConnectionFactory(): Mono<ConnectionFactory> {
        return determineCurrentLookupKey().handle { key: Any, sink: SynchronousSink<ConnectionFactory> ->
            if (resolvedConnectionFactories[key] == null) {
                loadConnections()
            }
            resolvedConnectionFactories[key]?.let {
                sink.next(it)
            } ?: sink.complete()
        }.switchIfEmpty(
            super.determineTargetConnectionFactory()
        )
    }

    @Value("\${config.multi-tenant.path}")
    private val multiTenantConfigPath: String = ""

    private fun loadConnections() {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.findAndRegisterModules()

        val connectionSpecs = mapper.readValue(File(multiTenantConfigPath), ConnectionSpecs::class.java).specs
        for (entry in connectionSpecs) {
            resolvedConnectionFactories[entry.key] = buildPostgresqlConnectionFactory(entry.value)
        }
    }

    fun updateConnections(name: String, connectionSpec: ConnectionSpec) {
        resolvedConnectionFactories[name] = buildPostgresqlConnectionFactory(connectionSpec)

        val sources = yamlMapper.readValue(
            File(multiTenantConfigPath), ConnectionSpecs::class.java
        ).specs.toMutableMap()
        sources[name] = connectionSpec

        yamlMapper.writeValue(File(multiTenantConfigPath), ConnectionSpecs(sources))
    }
}
