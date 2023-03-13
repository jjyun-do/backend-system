package com.samsung.healthcare.platform.adapter.persistence

import com.samsung.healthcare.platform.adapter.persistence.config.ReactivePostgresConfig
import com.samsung.healthcare.platform.adapter.persistence.entity.toEntity
import com.samsung.healthcare.platform.application.config.ProjectConfig.NewProjectConfig
import com.samsung.healthcare.platform.application.exception.DuplicateProjectNameException
import com.samsung.healthcare.platform.application.port.output.CreateProjectPort
import com.samsung.healthcare.platform.application.port.output.LoadProjectPort
import com.samsung.healthcare.platform.domain.Project
import com.samsung.healthcare.platform.domain.Project.ProjectId
import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toMono

@Component
class ProjectDatabaseAdapter(
    private val projectRepository: ProjectRepository,
    private val databaseClient: DatabaseClient,
    private val connectionFactoryOptionBuilder: ConnectionFactoryOptions.Builder,
    private val newProjectConfig: NewProjectConfig,
    private val reactivePostgresConfig: ReactivePostgresConfig,
) : CreateProjectPort, LoadProjectPort {
    override suspend fun create(project: Project): ProjectId {
        val projectId = try {
            ProjectId.from(
                projectRepository.save(project.toEntity()).id
            )
        } catch (_: DataIntegrityViolationException) {
            throw DuplicateProjectNameException()
        }

        val schemaName = schemaNameOf(projectId)

        createProjectSchema(schemaName)
            .then(createDatabaseConnection(schemaName))
            .flatMapMany { con -> createTables(con) }
            .awaitLast()

        reactivePostgresConfig.multiTenantRoutingConnectionFactory
            .registerProjectPostgresqlConnectionFactory(projectId.toString())

        return projectId
    }

    private fun schemaNameOf(projectId: ProjectId): String =
        "${newProjectConfig.newDatabaseConfig.prefix}${projectId.value}${newProjectConfig.newDatabaseConfig.postfix}"

    private fun createTables(con: Connection) = con.createBatch()
        .apply {
            newProjectConfig.schemas.forEach { add(it) }
        }
        .execute()

    private fun createDatabaseConnection(schemaName: String) =
        ConnectionFactories.get(
            connectionFactoryOptionBuilder.option(
                Option.valueOf("schema"), schemaName
            ).build()
        ).create()
            .toMono()

    private fun createProjectSchema(schemaName: String) =
        databaseClient.sql(createSchemaQuery(schemaName))
            .then()

    private fun createSchemaQuery(schemaName: String) = "CREATE SCHEMA $schemaName"

    override suspend fun findById(id: ProjectId): Project? =
        projectRepository.findById(id.value)
            ?.toDomain()

    override fun findAll(): Flow<Project> =
        projectRepository.findAll()
            .map { it.toDomain() }

    override fun findProjectByIdIn(idList: List<ProjectId>): Flow<Project> =
        projectRepository.findByIdIn(
            idList.map { it.value }
        ).map { it.toDomain() }

    override suspend fun existsById(id: ProjectId): Boolean =
        projectRepository.existsById(id.value)
}
