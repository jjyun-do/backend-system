package com.samsung.healthcare.dataqueryservice.adapter.persistence

import com.samsung.healthcare.dataqueryservice.adapter.persistence.common.get
import com.samsung.healthcare.dataqueryservice.application.config.ApplicationProperties
import com.samsung.healthcare.dataqueryservice.application.exception.ForbiddenSqlStatementTypeException
import com.samsung.healthcare.dataqueryservice.application.port.output.QueryDataPort
import com.samsung.healthcare.dataqueryservice.application.port.output.QueryDataResult
import io.trino.sql.parser.ParsingOptions
import io.trino.sql.parser.SqlParser
import io.trino.sql.tree.ShowCatalogs
import io.trino.sql.tree.ShowColumns
import io.trino.sql.tree.ShowSchemas
import io.trino.sql.tree.ShowSession
import io.trino.sql.tree.Statement
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

@Component
class DataStorageAdapter(
    private val config: ApplicationProperties,
) : QueryDataPort {
    private val parser: SqlParser = SqlParser()

    override fun executeQuery(projectId: String, accountId: String?, sql: String): QueryDataResult {
        require(projectId.isNotBlank())

        checkStatementType(parser.createStatement(sql, ParsingOptions()))

        val data = mutableListOf<Map<String, Any?>>()
        val columns = mutableListOf<String>()

        dbConnection(projectId, accountId).use { conn ->
            val resultSet = conn.prepareStatement(sql).executeQuery()
            val numCol: Int = resultSet.metaData.columnCount

            for (i in 1..numCol) {
                columns.add(resultSet.metaData.getColumnName(i))
            }
            while (resultSet.next()) {
                val datum = mutableMapOf<String, Any?>()
                for (i in 1..numCol) {
                    datum[resultSet.metaData.getColumnName(i)] = resultSet.get(i)
                }
                data.add(datum)
            }
        }

        return QueryDataResult(columns, data)
    }

    private fun dbConnection(projectId: String, accountId: String?): Connection {
        val properties = Properties()
        properties.setProperty("user", accountId ?: config.trino.user)

        return DriverManager.getConnection(
            "${config.trino.url}/${config.trino.catalog}/${config.db.prefix}${projectId}${config.db.postfix}",
            properties,
        )
    }

    private fun checkStatementType(statement: Statement) {
        when (statement) {
            is ShowCatalogs, is ShowColumns, is ShowSchemas, is ShowSession ->
                throw ForbiddenSqlStatementTypeException("statement object: $statement")
        }
    }
}
